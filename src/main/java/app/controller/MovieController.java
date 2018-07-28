package app.controller;

import app.entity.Film;
import app.entity.FilmList;
import app.entity.TopFilm;
import app.service.MovieService;
import app.vo.movie.MovieSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public void syncRecent() throws IOException {
        movieService.syncRecentMovies();
    }

    @PostMapping("sync/top")
    public void syncTop() throws IOException {
        movieService.syncTopMovies();
    }

    @GetMapping("movie/recent")
    public List<Film> getRecentMovie() {
        return movieService.getRecentFilmList();
    }

    @GetMapping("movie/top250")
    public List<TopFilm> getTopMovie() {
        return movieService.getTopFilmList();
    }

    @GetMapping("movie/subject/{id}")
    public MovieSubject getMovieSubject(@PathVariable Long id) throws IOException {
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
