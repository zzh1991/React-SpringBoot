package app.service.db;

import app.SpringBootBaseTest;
import app.constant.MovieTypeEnum;
import app.entity.Film;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DataServiceTest extends SpringBootBaseTest {
    private static final long MOVIE_ID = 1292052L;

    @Autowired
    private DataService dataService;

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    public void findByMovieId() {
        Film film = dataService.findByMovieId(MOVIE_ID);
        assertThat(film.getMovieId()).isEqualTo(MOVIE_ID);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    public void findByMovieIds() {
        List<Film> filmList = dataService.findByMovieIds(Lists.newArrayList(MOVIE_ID));
        assertThat(filmList.size()).isEqualTo(1);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    public void findByMovieTypeEnum() {
        List<Film> filmList = dataService.findByMovieTypeEnum(MovieTypeEnum.TOP);
        assertThat(filmList.isEmpty()).isEqualTo(false);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    public void listFilmsByMovieTypeEnum() {
        List<Film> filmList = dataService.findByMovieTypeEnum(MovieTypeEnum.TOP);
        assertThat(filmList.isEmpty()).isEqualTo(false);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    public void listAllFilms() {
        List<Film> filmList = dataService.listAllFilms();
        assertThat(filmList.isEmpty()).isEqualTo(false);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    public void saveAll() {
        Film film = dataService.findByMovieId(MOVIE_ID);
        dataService.saveAll(Lists.newArrayList(film));
        assertThat(Boolean.TRUE).isEqualTo(Boolean.TRUE);
    }
}