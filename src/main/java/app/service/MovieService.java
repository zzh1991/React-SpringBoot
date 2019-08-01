package app.service;

import app.aop.MethodTime;
import app.constant.MovieTypeEnum;
import app.dao.FilmListRepository;
import app.entity.FilmList;
import app.mapper.FilmListMapper;
import app.vo.movie.Avatar;
import app.vo.movie.Movie;
import app.vo.movie.MovieSubject;
import app.vo.movie.MovieVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
    private static final ExecutorService executorService =
            new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>());

    private FilmListRepository filmListRepository;
    private FilmListMapper filmListMapper;

    @MethodTime
    public void syncMovies(MovieTypeEnum movieTypeEnum) {
        this.saveMovie(movieTypeEnum);
        this.saveDetailToMovie(movieTypeEnum);
    }

    private List<Movie> getMovies(String url) {
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

    private String getUrlContent(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void saveMovie(MovieTypeEnum movieTypeEnum) {
        String url = "https://douban.uieee.com/v2/movie/in_theaters?city=上海";
        if (MovieTypeEnum.TOP.equals(movieTypeEnum)) {
            url = "https://douban.uieee.com/v2/movie/top250?start=0&count=100";
        }
        List<Movie> movieList = getMovies(url);
        if (!movieList.isEmpty()) {
            this.deleteOutDataMovie(movieTypeEnum);
        }
        this.saveFilmList(movieList, movieTypeEnum);
    }

    private void saveFilmList(List<Movie> movieList, MovieTypeEnum movieTypeEnum) {
        List<FilmList> filmList = Lists.newArrayList();
        for (Movie movie : movieList) {
            FilmList film = filmListRepository.findFirstByMovieId(movie.getId());
            FilmList newFilm = FilmList.builder()
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

    public List<FilmList> getFilmList(MovieTypeEnum movieTypeEnum) {
        return filmListMapper.selectList(Wrappers.<FilmList>lambdaQuery()
                .eq(FilmList::getMovieTypeEnum, movieTypeEnum)
                .orderByDesc(FilmList::getRating)
        );
    }

    private void deleteOutDataMovie(MovieTypeEnum movieTypeEnum) {
        List<FilmList> filmList = filmListRepository.findByMovieTypeEnumOrderByRatingDesc(movieTypeEnum);
        filmList.forEach(film -> film.setMovieTypeEnum(MovieTypeEnum.NORMAL));
        filmListRepository.saveAll(filmList);
        log.info("set old recent {} movies to normal movies", filmList.size());
    }

    private String getNames(List<Avatar> avatars) {
        List<String> nameList = Lists.newArrayList();
        avatars.forEach(avatar ->
            nameList.add(avatar.getName())
        );
        return StringUtils.join(nameList.toArray(), SEPARATOR);
    }

    public MovieSubject getMovieSubject(Long id) {
        String url = "https://douban.uieee.com/v2/movie/subject/" + id;
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

    private void saveDetailToMovie(MovieTypeEnum movieTypeEnum) {
        List<FilmList> filmList = filmListRepository.findByMovieTypeEnumOrderByRatingDesc(movieTypeEnum);
        List<FilmList> newFilmList = Lists.newArrayList();
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
                log.error("get movie summary error");
            }
        }

        batchUpdateFilmList(newFilmList);
    }

    private void batchUpdateFilmList(List<FilmList> newFilmList) {
        if (!newFilmList.isEmpty()) {
            filmListRepository.saveAll(newFilmList);
            log.info("update {} movie items summary and country", newFilmList.size());
        }
    }

    public FilmList getFilmListById(Long id) {
        return filmListRepository.findFirstByMovieId(id);
    }

    public List<FilmList> getStarList(List<Long> movieIdList) {
        return getFilmLists(movieIdList);
    }

    private List<FilmList> getFilmLists(List<Long> movieIdList) {
        movieIdList = movieIdList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<FilmList> filmLists = filmListRepository.findByMovieIdIsIn(movieIdList);
        List<Long> existedIdList = filmLists.stream()
                .filter(filmList -> !Objects.isNull(filmList.getSummary()))
                .map(FilmList::getMovieId).collect(Collectors.toList());
        List<FilmList> newFilmList = Lists.newArrayList();
        for (Long movieId : movieIdList) {
            if (!existedIdList.contains(movieId)) {
                FilmList syncedMovie = this.syncOneMovieToMovieList(movieId);
                if (Objects.nonNull(syncedMovie)) {
                    newFilmList.add(syncedMovie);
                }
            }
        }

        batchUpdateFilmList(newFilmList);
        return filmListRepository.findByMovieIdIsIn(movieIdList);
    }

    public List<FilmList> getViewedList(List<Long> movieIdList) {
        return getFilmLists(movieIdList);
    }

    public FilmList syncOneMovieToMovieList(Long movieId) {
        FilmList filmList = filmListRepository.findFirstByMovieId(movieId);
        MovieSubject movieSubject;
        FilmList syncedMovie;
        try {
            movieSubject = getMovieSubject(movieId);
        } catch (Exception e) {
            log.error("can not fetch this movieId: {}", movieId);
            return null;
        }

        if (!Objects.isNull(movieSubject)) {
            syncedMovie = FilmList.builder()
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

        if (!Objects.isNull(filmList)) {
            syncedMovie.setId(filmList.getId());
        }

        return syncedMovie;
    }

    public List<FilmList> getAllMoviesList() {
        return filmListMapper.selectList(Wrappers.<FilmList>lambdaQuery()
                .orderByDesc(FilmList::getMovieYear)
                .orderByDesc(FilmList::getRating)
        );
    }
}