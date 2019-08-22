package app.service;

import app.constant.MovieTypeEnum;
import app.entity.Film;
import app.service.db.DataService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private DataService dataService;

    private static final Film FILM = Film.builder()
            .id(1L)
            .movieId(1L)
            .build();

    private static final List<Film> FILM_LIST = Lists.newArrayList(FILM);

    @Test
    public void findByMovieId() {
        when(dataService.findByMovieId(FILM.getMovieId())).thenReturn(FILM);
        Film result = movieService.getMovieById(FILM.getMovieId());
        assertThat(result).isEqualTo(FILM);
    }

    @Test
    public void getMoviesByMovieTypeEnum() {
        when(dataService.listFilmsByMovieTypeEnum(MovieTypeEnum.TOP)).thenReturn(FILM_LIST);
        List<Film> filmList = movieService.getMoviesByMovieTypeEnum(MovieTypeEnum.TOP);
        assertThat(filmList.size()).isEqualTo(FILM_LIST.size());
    }

    @Test
    public void getAllMovies() {
        when(dataService.listAllFilms()).thenReturn(FILM_LIST);
        List<Film> filmList = movieService.getAllMovies();
        assertThat(filmList.size()).isEqualTo(FILM_LIST.size());
    }
}