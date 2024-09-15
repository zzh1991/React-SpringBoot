package com.zzhpro.movie.dao;

import com.google.common.collect.Lists;
import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.entity.Film;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FilmRepositoryTest {
    private static final long MOVIE_ID = 1292052L;
    @Autowired
    private FilmRepository filmRepository;

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void findFirstByMovieId() {
        Film film = filmRepository.findFirstByMovieId(MOVIE_ID);
        assertThat(film.getMovieId()).isEqualTo(MOVIE_ID);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void findByMovieIdIsIn() {
        List<Film> filmList = filmRepository.findByMovieIdIsIn(Lists.newArrayList(MOVIE_ID));
        assertThat(filmList).hasSize(1);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void findAllByOrderByMovieYearDescRatingDesc() {
        List<Film> filmList = filmRepository.findAllByOrderByMovieYearDescRatingDesc();
        assertThat(filmList).isNotEmpty();
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void findByMovieTypeEnumOrderByRatingDesc() {
        List<Film> filmList = filmRepository.findByMovieTypeEnumOrderByRatingDesc(MovieTypeEnum.TOP);
        assertThat(filmList).isNotEmpty();
    }
}