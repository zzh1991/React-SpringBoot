package com.zzhpro.movie.service.db;

import com.google.common.collect.Lists;
import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.entity.Film;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DataServiceTest {
    private static final long MOVIE_ID = 1292052L;

    @Autowired
    private DataService dataService;

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void findByMovieId() {
        Film film = dataService.findByMovieId(MOVIE_ID);
        assertThat(film.getMovieId()).isEqualTo(MOVIE_ID);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void findByMovieIds() {
        List<Film> filmList = dataService.findByMovieIds(Lists.newArrayList(MOVIE_ID));
        assertThat(filmList.size()).isEqualTo(1);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void findByMovieTypeEnum() {
        List<Film> filmList = dataService.findByMovieTypeEnum(MovieTypeEnum.TOP);
        assertThat(filmList.isEmpty()).isFalse();
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void listFilmsByMovieTypeEnum() {
        List<Film> filmList = dataService.findByMovieTypeEnum(MovieTypeEnum.TOP);
        assertThat(filmList.isEmpty()).isFalse();
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void listAllFilms() {
        List<Film> filmList = dataService.listAllFilms();
        assertThat(filmList.isEmpty()).isFalse();
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void saveAll() {
        Film film = dataService.findByMovieId(MOVIE_ID);
        dataService.saveAll(Lists.newArrayList(film));
    }
}