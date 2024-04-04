package com.zzhpro.movie.controller;

import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.entity.Film;
import com.zzhpro.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhihao zhang
 * @since 2017.10.18
 */

@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;

    /**
     * get recent movies
     * @return
     */
    @GetMapping("movie/recent")
    public List<Film> getRecentMovie() {
        return movieService.getMoviesByMovieTypeEnum(MovieTypeEnum.RECENT);
    }

    @GetMapping("movie/top250")
    public List<Film> getTopMovie() {
        return movieService.getMoviesByMovieTypeEnum(MovieTypeEnum.TOP);
    }

    @GetMapping("movie/all")
    public List<Film> getAllMoviesList() {
        return movieService.getAllMovies();
    }

    @PostMapping("sync/recent")
    public void syncRecent() {
        movieService.syncMovies(MovieTypeEnum.RECENT);
    }

    @PostMapping("sync/top")
    public void syncTop() {
        movieService.syncMovies(MovieTypeEnum.TOP);
    }

    /**
     * find film by movie id
     * @param id movie id
     * @return film info
     */
    @GetMapping("list/{id}")
    public Film getFilmListById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }
}
