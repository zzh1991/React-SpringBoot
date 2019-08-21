package app.controller;

import app.constant.MovieTypeEnum;
import app.entity.Film;
import app.service.MovieService;
import app.vo.movie.MovieSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhihao zhang
 * @date 2017.10.18
 */

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("sync/recent")
    public void syncRecent() {
        movieService.syncMovies(MovieTypeEnum.RECENT);
    }

    @PostMapping("sync/top")
    public void syncTop() {
        movieService.syncMovies(MovieTypeEnum.TOP);
    }

    @GetMapping("movie/recent")
    public List<Film> getRecentMovie() {
        return movieService.getMoviesByMovieTypeEnum(MovieTypeEnum.RECENT);
    }

    @GetMapping("movie/top250")
    public List<Film> getTopMovie() {
        return movieService.getMoviesByMovieTypeEnum(MovieTypeEnum.TOP);
    }

    @GetMapping("movie/subject/{id}")
    public MovieSubject getMovieSubject(@PathVariable Long id) {
        return movieService.getMovieSubject(id);
    }

    @GetMapping("movie/viewed/{id}/{viewed}")
    public Boolean updateMovieViewedState(@PathVariable Long id, @PathVariable Boolean viewed) {
        return true;
    }

    @GetMapping("list/{id}")
    public Film getFilmListById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PostMapping("movie/star")
    public List<Film> getStarList(@RequestBody List<Long> movieIdList) {
        return movieService.getMoviesByMovieIds(movieIdList);
    }

    @PostMapping("movie/viewed")
    public List<Film> getViewedList(@RequestBody List<Long> movieIdList) {
        return movieService.getMoviesByMovieIds(movieIdList);
    }

    @PostMapping("movie/sync/{movieId}")
    public Film syncOneMovieToMovieList(@PathVariable Long movieId) {
        return movieService.syncMovieByMovieId(movieId);
    }

    @GetMapping("movie/all")
    public List<Film> getAllMoviesList() {
        return movieService.getAllMovies();
    }
}
