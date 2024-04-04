package com.zzhpro.movie.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.entity.Film;
import jakarta.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zhihao zhang
 * @since 5/11/22
 */
@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FilmMapper2Test {
    @Autowired
    private FilmMapper filmMapper;

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void getById() {
        Film film = filmMapper.selectById(340);
        assertThat(Objects.nonNull(film)).isTrue();
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void listFilmsByMovieTypeEnum() {
        List<Film> filmList = filmMapper.selectList(Wrappers.<Film>lambdaQuery()
                .eq(Film::getMovieTypeEnum, MovieTypeEnum.TOP)
                .orderByDesc(Film::getRating)
        );
        assertThat(CollectionUtils.isNotEmpty(filmList)).isTrue();
    }
}
