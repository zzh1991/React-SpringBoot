package app.service;

import app.dao.FilmRepository;
import app.entity.Film;
import app.mapper.FilmMapper;
import app.service.db.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MovieServiceTest {

    @Mock
    FilmRepository filmRepository;

    @InjectMocks
    MovieService movieService;

    @Mock
    FilmMapper filmMapper;

    @Mock
    DataService dataService;


    @Test
    public void findByMovieId() {
        Film film = Film.builder()
                .id(1L)
                .movieId(1L)
                .build();

//        when(filmRepository.findFirstByMovieId(film.getMovieId())).thenReturn(film);
        when(dataService.findByMovieId(film.getMovieId())).thenReturn(film);

        Film result = movieService.getMovieById(film.getMovieId());
        assertThat(result).isEqualTo(film);
    }
}