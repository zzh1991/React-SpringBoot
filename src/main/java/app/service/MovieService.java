package app.service;

import app.aop.MethodTime;
import app.constant.MovieTypeEnum;
import app.entity.Film;
import app.service.db.DataService;
import app.vo.movie.Avatar;
import app.vo.movie.Movie;
import app.vo.movie.MovieSubject;
import app.vo.movie.MovieVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author zhihao zhang
 * @date 2017.10.18
 */

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieService {

    private static final String SEPARATOR = ",";
    private static final String LARGE = "large";
    private static final String DOUBAN_URL = "https://douban.uieee.com";

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();
    private static final ExecutorService executorService =
            new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>());

    private DataService dataService;

    public Film getMovieById(Long id) {
        return dataService.findByMovieId(id);
    }

    public List<Film> getMoviesByMovieTypeEnum(MovieTypeEnum movieTypeEnum) {
        return dataService.listFilmsByMovieTypeEnum(movieTypeEnum);
    }

    public List<Film> getAllMovies() {
        return dataService.listAllFilms();
    }

    public List<Film> getMoviesByMovieIds(List<Long> movieIdList) {
        movieIdList = movieIdList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Film> filmList = dataService.findByMovieIds(movieIdList);
        List<Long> existedIdList = filmList.stream()
                .filter(film -> !Objects.isNull(film.getSummary()))
                .map(Film::getMovieId).collect(Collectors.toList());
        List<Film> newFilmList = Lists.newArrayList();
        for (Long movieId : movieIdList) {
            if (!existedIdList.contains(movieId)) {
                Film syncedMovie = this.syncMovieByMovieId(movieId);
                if (Objects.nonNull(syncedMovie)) {
                    newFilmList.add(syncedMovie);
                }
            }
        }

        batchUpdateFilmList(newFilmList);
        return dataService.findByMovieIds(movieIdList);
    }

    @MethodTime
    public void syncMovies(MovieTypeEnum movieTypeEnum) {
        this.saveMovie(movieTypeEnum);
        this.saveDetailToMovie(movieTypeEnum);
    }

    private void saveMovie(MovieTypeEnum movieTypeEnum) {
        String url = DOUBAN_URL.concat("/v2/movie/in_theaters?city=上海");
        if (MovieTypeEnum.TOP.equals(movieTypeEnum)) {
            url = DOUBAN_URL.concat("/v2/movie/top250?start=0&count=100");
        }
        List<Movie> movieList = getMoviesFromOrigin(url);
        if (Objects.nonNull(movieList) && !movieList.isEmpty()) {
            this.deleteOutDataMovie(movieTypeEnum);
            this.saveFilmList(movieList, movieTypeEnum);
        }
    }

    private String getUrlContent(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = HTTP_CLIENT.newCall(request).execute();
        return response.body().string();
    }

    private List<Movie> getMoviesFromOrigin(String url) {
        try {
            String context = getUrlContent(url);
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            MovieVo movieVo = mapper.readValue(context,
                    TypeFactory.defaultInstance().constructType(MovieVo.class));
            return movieVo.getSubjects();
        } catch (Exception e) {
            log.error("failed to get recent movie info: ", e);
            return Lists.newArrayList();
        }
    }

    private void deleteOutDataMovie(MovieTypeEnum movieTypeEnum) {
        List<Film> filmList = dataService.findByMovieTypeEnum(movieTypeEnum);
        filmList.forEach(film -> film.setMovieTypeEnum(MovieTypeEnum.NORMAL));
        dataService.saveAll(filmList);
        log.info("set old recent {} movies to normal movies", filmList.size());
    }

    private void saveFilmList(List<Movie> movieList, MovieTypeEnum movieTypeEnum) {
        List<Film> filmList = Lists.newArrayList();
        for (Movie movie : movieList) {
            Film film = dataService.findByMovieId(movie.getId());
            Film newFilm = Film.builder()
                    .movieId(movie.getId())
                    .title(movie.getTitle())
                    .rating(movie.getRating().getAverage())
                    .url(movie.getAlt())
                    .movieYear(movie.getYear())
                    .imageLarge(movie.getImages().get(LARGE))
                    .casts(getNames(movie.getCasts()))
                    .directors(getNames(movie.getDirectors()))
                    .genres(StringUtils.join(movie.getGenres(), SEPARATOR))
                    .updateTime(new Date(System.currentTimeMillis()))
                    .movieTypeEnum(movieTypeEnum)
                    .build();
            if (Objects.nonNull(film)) {
                newFilm.setCountries(film.getCountries());
                newFilm.setSummary(film.getSummary());
                newFilm.setId(film.getId());
            }
            filmList.add(newFilm);
        }
        batchUpdateFilmList(filmList);
    }

    private void batchUpdateFilmList(List<Film> filmList) {
        if (!filmList.isEmpty()) {
            dataService.saveAll(filmList);
            log.info("update {} movie items summary and country", filmList.size());
        }
    }

    private void saveDetailToMovie(MovieTypeEnum movieTypeEnum) {
        List<Film> filmList = dataService.findByMovieTypeEnum(movieTypeEnum);
        List<Film> newFilmList = Lists.newArrayList();
        List<CompletableFuture<Boolean>> completableFuture =
                filmList.stream()
                        .filter(film -> Strings.isNullOrEmpty(film.getSummary()))
                        .map(film -> CompletableFuture.supplyAsync(() -> getMovieSubject(film.getMovieId()),
                                executorService)
                                .thenApply(movieSubject -> {
                                    if (Objects.nonNull(movieSubject)) {
                                        film.setSummary(movieSubject.getSummary());
                                        film.setCountries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR));
                                        newFilmList.add(film);
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

        batchUpdateFilmList(newFilmList);
    }

    public Film syncMovieByMovieId(Long movieId) {
        Film film = dataService.findByMovieId(movieId);
        MovieSubject movieSubject;
        Film syncedMovie;
        try {
            movieSubject = getMovieSubject(movieId);
        } catch (Exception e) {
            log.error("can not fetch this movieId: {}", movieId);
            return null;
        }

        if (!Objects.isNull(movieSubject)) {
            syncedMovie = Film.builder()
                    .movieId(movieId)
                    .title(movieSubject.getTitle())
                    .rating(movieSubject.getRating().getAverage())
                    .url(movieSubject.getAlt())
                    .movieYear(movieSubject.getYear())
                    .imageLarge(movieSubject.getImages().get(LARGE))
                    .casts(getNames(movieSubject.getCasts()))
                    .directors(getNames(movieSubject.getDirectors()))
                    .genres(StringUtils.join(movieSubject.getGenres(), SEPARATOR))
                    .summary(movieSubject.getSummary())
                    .countries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR))
                    .movieTypeEnum(MovieTypeEnum.NORMAL)
                    .updateTime(new Date(System.currentTimeMillis()))
                    .build();
        } else {
            return null;
        }

        if (!Objects.isNull(film)) {
            syncedMovie.setId(film.getId());
        }

        return syncedMovie;
    }

    public MovieSubject getMovieSubject(Long id) {
        String url = DOUBAN_URL.concat("/v2/movie/subject/")
                .concat(String.valueOf(id));
        MovieSubject movieSubject;
        try {
            String context = getUrlContent(url);
            ObjectMapper mapper = new ObjectMapper();
            movieSubject = mapper.readValue(context, TypeFactory.defaultInstance().constructType(MovieSubject.class));
        } catch (Exception e) {
            return null;
        }
        return movieSubject;
    }

    private String getNames(List<Avatar> avatars) {
        List<String> nameList = Lists.newArrayList();
        avatars.forEach(avatar ->
                nameList.add(avatar.getName())
        );
        return StringUtils.join(nameList.toArray(), SEPARATOR);
    }
}