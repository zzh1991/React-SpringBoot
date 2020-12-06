package app.service;

import app.SpringTestConfiguration;
import app.constant.MovieTypeEnum;
import app.entity.Film;
import app.service.db.DataService;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringTestConfiguration.class })
class MovieServiceTest {

    @Autowired
    @Qualifier("movieServiceTest")
    private MovieService movieService;

    @MockBean
    private DataService dataService;

    private static final Film FILM = Film.builder()
            .id(1L)
            .movieId(1L)
            .build();

    private static final List<Film> FILM_LIST = Lists.newArrayList(FILM);

    @Test
    void findByMovieId() {
        when(dataService.findByMovieId(FILM.getMovieId())).thenReturn(FILM);
        Film result = movieService.getMovieById(FILM.getMovieId());
        assertThat(result).isEqualTo(FILM);
    }

    @Test
    void getMoviesByMovieTypeEnum() {
        when(dataService.listFilmsByMovieTypeEnum(MovieTypeEnum.TOP)).thenReturn(FILM_LIST);
        List<Film> filmList = movieService.getMoviesByMovieTypeEnum(MovieTypeEnum.TOP);
        assertThat(filmList.size()).isEqualTo(FILM_LIST.size());
    }

    @Test
    void getAllMovies() {
        when(dataService.listAllFilms()).thenReturn(FILM_LIST);
        List<Film> filmList = movieService.getAllMovies();
        assertThat(filmList.size()).isEqualTo(FILM_LIST.size());
    }
}