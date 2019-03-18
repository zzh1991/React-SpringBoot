package app.service;

import app.dao.FilmListRepository;
import app.entity.FilmList;
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
    FilmListRepository filmListRepository;

    @InjectMocks
    MovieService movieService;


    @Test
    public void getFilmListById() {
        FilmList filmList = FilmList.builder()
                .id(1L)
                .movieId(1L)
                .build();

        when(filmListRepository.findFirstByMovieId(filmList.getMovieId())).thenReturn(filmList);

        FilmList result = movieService.getFilmListById(filmList.getMovieId());
        assertThat(result).isEqualTo(filmList);
    }
}