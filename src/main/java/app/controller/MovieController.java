package app.controller;

import app.constant.MovieTypeEnum;
import app.entity.FilmList;
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
    public List<FilmList> getRecentMovie() {
        return movieService.getFilmList(MovieTypeEnum.RECENT);
    }

    @GetMapping("movie/top250")
    public List<FilmList> getTopMovie() {
        return movieService.getFilmList(MovieTypeEnum.TOP);
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
    public FilmList getFilmListById(@PathVariable Long id) {
        return movieService.getFilmListById(id);
    }

    @PostMapping("movie/star")
    public List<FilmList> getStarList(@RequestBody List<Long> movieIdList) {
        return movieService.getStarList(movieIdList);
    }

    @PostMapping("movie/viewed")
    public List<FilmList> getViewedList(@RequestBody List<Long> movieIdList) {
        return movieService.getViewedList(movieIdList);
    }

    @PostMapping("movie/sync/{movieId}")
    public FilmList syncOneMovieToMovieList(@PathVariable Long movieId) {
        return movieService.syncOneMovieToMovieList(movieId);
    }

    @GetMapping("movie/all")
    public List<FilmList> getAllMoviesList() {
        return movieService.getAllMoviesList();
    }
}
