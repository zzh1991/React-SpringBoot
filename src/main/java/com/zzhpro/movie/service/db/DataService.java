package com.zzhpro.movie.service.db;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.dao.FilmRepository;
import com.zzhpro.movie.entity.Film;
import com.zzhpro.movie.mapper.FilmMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhihao zhang
 * @since 2019-08-21
 */

@Service
@Slf4j
public class DataService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmMapper filmMapper;

    public Film findByMovieId(long movieId) {
        return filmRepository.findFirstByMovieId(movieId);
    }

    public List<Film> findByMovieIds(List<Long> movieIdList) {
        return filmRepository.findByMovieIdIsIn(movieIdList);
    }

    public List<Film> findByMovieTypeEnum(MovieTypeEnum movieTypeEnum) {
        return filmRepository.findByMovieTypeEnumOrderByRatingDesc(movieTypeEnum);
    }

    public List<Film> listFilmsByMovieTypeEnum(MovieTypeEnum movieTypeEnum) {
        return filmMapper.selectList(Wrappers.<Film>lambdaQuery()
                .eq(Film::getMovieTypeEnum, movieTypeEnum)
                .orderByDesc(Film::getRating)
        );
    }

    public List<Film> listAllFilms() {
        return filmMapper.selectList(Wrappers.<Film>lambdaQuery()
                .orderByDesc(Film::getMovieYear)
                .orderByDesc(Film::getRating)
        );
    }

    public void saveAll(List<Film> filmList) {
        filmRepository.saveAll(filmList);
    }

    public void save(Film film) {
        filmRepository.save(film);
    }
}
