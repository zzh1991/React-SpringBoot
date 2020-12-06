package app.mapper;

import app.constant.MovieTypeEnum;
import app.entity.Film;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FilmMapperTest {
    @Autowired
    private FilmMapper filmMapper;

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void listAllFilms() {
        List<Film> filmList = filmMapper.selectList(Wrappers.<Film>lambdaQuery()
                .orderByDesc(Film::getMovieYear)
                .orderByDesc(Film::getRating)
        );
        assertThat(filmList.isEmpty()).isEqualTo(false);
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-film-data.sql" })
    void listFilmsByMovieTypeEnum() {
        List<Film> filmList = filmMapper.selectList(Wrappers.<Film>lambdaQuery()
                .eq(Film::getMovieTypeEnum, MovieTypeEnum.TOP)
                .orderByDesc(Film::getRating)
        );
        assertThat(filmList.isEmpty()).isEqualTo(false);
    }
}