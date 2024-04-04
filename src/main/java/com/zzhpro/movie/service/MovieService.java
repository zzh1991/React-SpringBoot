package com.zzhpro.movie.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zzhpro.movie.aop.MethodTime;
import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.entity.Film;
import com.zzhpro.movie.service.db.DataService;
import com.zzhpro.movie.util.JsoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class MovieService {
    private static final ExecutorService executorService =
            new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>());

    @Autowired
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
        this.saveFilmList(filmList);
    }

    private void deleteOutDatedMovie(MovieTypeEnum movieTypeEnum) {
        List<Film> filmList = dataService.findByMovieTypeEnum(movieTypeEnum);
        filmList.forEach(film -> film.setMovieTypeEnum(MovieTypeEnum.NORMAL));
        dataService.saveAll(filmList);
        log.info("set old {} {} movies to normal movies", movieTypeEnum, filmList.size());
    }

    private void saveFilmList(List<Film> movieList) {
        List<Film> filmList = movieList.stream()
                .map(movie -> Film.transformMovieAndOldFilmToNewFilm(
                        movie, dataService.findByMovieId(movie.getMovieId())))
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
                                        newFilmList.add(Film.transformMovieAndOldFilmToNewFilm(film, movieSubject));
                                        return true;
                                    }
                                    return false;
                                }))
                        .collect(Collectors.toList());

        for (Future<Boolean> future : completableFuture) {
            try {
                boolean fetchStatus = future.get();
                log.warn("update summary success: {}", fetchStatus);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.error("get movie summary error", e);
            }
        }

        this.batchUpdateFilmList(newFilmList);
    }
}