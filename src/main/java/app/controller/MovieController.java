package app.controller;

import app.entity.Film;
import app.entity.TopFilm;
import app.service.MovieService;
import app.vo.movie.MovieSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("sync")
    public void sync() throws IOException {
        movieService.sync();
    }

    @GetMapping("movie/recent")
    public List<Film> getRecentMovie() throws IOException {
        return movieService.getFilmList();
    }

    @GetMapping("movie/top250")
    public List<TopFilm> getTopMovie() throws IOException {
        return movieService.getTopFilmList();
    }

    @GetMapping("movie/subject/{id}")
    public MovieSubject getMovieSubject(@PathVariable Long id) throws IOException {
        return movieService.getMovieSubject(id);
    }

    @GetMapping("movie/viewed/{id}/{viewed}")
    public Boolean updateMovieViewedState(@PathVariable Long id, @PathVariable Boolean viewed) {
        movieService.setViewedState(id, viewed);
        return true;
    }

}
