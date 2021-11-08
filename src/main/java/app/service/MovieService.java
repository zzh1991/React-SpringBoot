package app.service;

import app.aop.MethodTime;
import app.constant.MovieTypeEnum;
import app.entity.Film;
import app.service.db.DataService;
import app.util.JsoupUtils;
import app.vo.movie.MovieSubject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhihao zhang
 * @since 2017.10.18
 */

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieService {
    private static final String DOUBAN_URL = "https://douban.uieee.com";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();
    private static final ExecutorService executorService =
            new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>());

    private DataService dataService;

    public List<Film> getMoviesByMovieTypeEnum(MovieTypeEnum movieTypeEnum) {
        return dataService.listFilmsByMovieTypeEnum(movieTypeEnum);
    }

    public List<Film> getAllMovies() {
        return dataService.listAllFilms();
    }

    public Film getMovieById(Long id) {
        return dataService.findByMovieId(id);
    }

    public List<Film> getMoviesByMovieIds(List<Long> movieIdList) {
        List<Long> filterMovieIdList = movieIdList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Long> existedIdList = dataService.findByMovieIds(filterMovieIdList).stream()
                .filter(film -> Objects.nonNull(film.getSummary()))
                .map(Film::getMovieId)
                .collect(Collectors.toList());
        List<Film> newFilmList = filterMovieIdList.stream()
                .filter(movieId -> !existedIdList.contains(movieId))
                .map(movieId -> syncMovieByMovieId(movieId, false))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        this.batchUpdateFilmList(newFilmList);
        return dataService.findByMovieIds(filterMovieIdList);
    }

    public Film syncMovieByMovieId(Long movieId, boolean save) {
        Film film = dataService.findByMovieId(movieId);
        MovieSubject movieSubject;
        try {
            movieSubject = getMovieSubject(movieId);
        } catch (Exception e) {
            log.error("can not fetch this movieId: {}", movieId);
            return null;
        }

        if (Objects.isNull(movieSubject)) {
            return null;
        }
        Film syncedFilm = Film.transformMovieSubjectToFilm(movieSubject, MovieTypeEnum.NORMAL);

        if (Objects.nonNull(film)) {
            syncedFilm.setId(film.getId());
        }

        if (save) {
            dataService.save(syncedFilm);
        }

        return syncedFilm;
    }

    public MovieSubject getMovieSubject(Long id) {
        String url = DOUBAN_URL.concat("/v2/movie/subject/").concat(String.valueOf(id));
        try {
            return MAPPER.readValue(getUrlContent(url),
                    TypeFactory.defaultInstance().constructType(MovieSubject.class));
        } catch (Exception e) {
            log.error("failed to get movie subject by {}", url, e);
            return null;
        }
    }

    @MethodTime
    public void syncMovies(MovieTypeEnum movieTypeEnum) {
        this.saveMovie(movieTypeEnum);
        this.saveDetailToMovie(movieTypeEnum);
    }

    private void saveMovie(MovieTypeEnum movieTypeEnum) {
        List<Film> filmList;
        if (MovieTypeEnum.TOP.equals(movieTypeEnum)) {
            filmList = JsoupUtils.getTopFilmListFromDouban();
        } else if (MovieTypeEnum.RECENT.equals(movieTypeEnum)) {
            filmList = JsoupUtils.getFilmListFromDouban();
        } else {
            return;
        }
        if (CollectionUtils.isEmpty(filmList)) {
            return;
        }
        this.deleteOutDatedMovie(movieTypeEnum);
        this.saveFilmList(filmList, movieTypeEnum);
    }

    private String getUrlContent(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = HTTP_CLIENT.newCall(request).execute();
        assert response.body() != null;
        return response.body().string();
    }

    private void deleteOutDatedMovie(MovieTypeEnum movieTypeEnum) {
        List<Film> filmList = dataService.findByMovieTypeEnum(movieTypeEnum);
        filmList.forEach(film -> film.setMovieTypeEnum(MovieTypeEnum.NORMAL));
        dataService.saveAll(filmList);
        log.info("set old {} {} movies to normal movies", movieTypeEnum, filmList.size());
    }

    private void saveFilmList(List<Film> movieList, MovieTypeEnum movieTypeEnum) {
        List<Film> filmList = movieList.stream()
                .map(movie -> Film.transformMovieAndOldFilmToNewFilm(
                        movie, movieTypeEnum, dataService.findByMovieId(movie.getMovieId())))
                .collect(Collectors.toList());
        this.batchUpdateFilmList(filmList);
    }

    private void batchUpdateFilmList(List<Film> filmList) {
        if (CollectionUtils.isEmpty(filmList)) {
            return;
        }
        dataService.saveAll(filmList);
        log.info("update {} movie items summary and country", filmList.size());
    }

    private void saveDetailToMovie(MovieTypeEnum movieTypeEnum) {
        List<Film> filmList = dataService.findByMovieTypeEnum(movieTypeEnum);
        List<Film> newFilmList = Lists.newArrayList();
        List<CompletableFuture<Boolean>> completableFuture =
                filmList.stream()
                        .filter(film -> Strings.isNullOrEmpty(film.getSummary()))
                        .map(film -> CompletableFuture.supplyAsync(() -> JsoupUtils
                                        .getFilmDetailByMovieTypeAndUrl(movieTypeEnum, film.getUrl()),
                                executorService)
                                .thenApply(movieSubject -> {
                                    if (Objects.nonNull(movieSubject)) {
                                        newFilmList.add(Film.transformMovieAndOldFilmToNewFilm(film, movieTypeEnum, movieSubject));
                                        return true;
                                    }
                                    return false;
                                }))
                        .collect(Collectors.toList());

        for (Future<Boolean> future : completableFuture) {
            try {
                boolean fetchStatus = future.get();
                log.warn("update summary success: {}", fetchStatus);
            } catch (Exception e) {
                log.error("get movie summary error", e);
            }
        }

        this.batchUpdateFilmList(newFilmList);
    }
}