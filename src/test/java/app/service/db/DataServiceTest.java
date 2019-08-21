package app.service.db;

import app.dao.FilmRepository;
import app.entity.Film;
import app.mapper.FilmMapper;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DataServiceTest {

    @Mock
    FilmRepository filmRepository;

    @Mock
    FilmMapper filmMapper;

    @InjectMocks
    DataService dataService;

    private static final List<Film> filmList = Lists.newArrayList();

    @Before
    public void setUp() throws Exception {
        filmList.add(Film.builder().id(1L).movieId(1L).build());
        filmList.add(Film.builder().id(2L).movieId(2L).build());
        filmList.add(Film.builder().id(3L).movieId(3L).build());
    }

    @Test
    public void findByMovieId() {
        Film film = filmList.get(0);
        when(filmRepository.findFirstByMovieId(film.getMovieId())).thenReturn(film);
        Film result = dataService.findByMovieId(film.getMovieId());
        assertThat(result).isEqualTo(film);
    }

    @Test
    public void findByMovieIds() {
    }

    @Test
    public void findByMovieTypeEnum() {
    }

    @Test
    public void listFilmsByMovieTypeEnum() {
    }

    @Test
    public void listAllFilms() {
    }

    @Test
    public void saveAll() {
    }
}