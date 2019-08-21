package app.dao;

import static org.assertj.core.api.Assertions.assertThat;

import app.SpringBootBaseTest;
import app.constant.MovieTypeEnum;
import app.entity.Film;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;

public class FilmRepositoryTest extends SpringBootBaseTest {
    @Autowired
    private FilmRepository filmRepository;

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    public void findFirstByMovieId() {
        Film film = filmRepository.findFirstByMovieId(1292052L);
        assertThat(film.getMovieId()).isEqualTo(1292052L);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    public void findByMovieIdIsIn() {
        List<Film> filmList = filmRepository.findByMovieIdIsIn(Lists.newArrayList(1292052L));
        assertThat(filmList.size()).isEqualTo(1);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    public void findAllByOrderByMovieYearDescRatingDesc() {
        List<Film> filmList = filmRepository.findAllByOrderByMovieYearDescRatingDesc();
        assertThat(filmList.isEmpty()).isEqualTo(false);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    public void findByMovieTypeEnumOrderByRatingDesc() {
        List<Film> filmList = filmRepository.findByMovieTypeEnumOrderByRatingDesc(MovieTypeEnum.TOP);
        assertThat(filmList.isEmpty()).isEqualTo(false);
    }
}