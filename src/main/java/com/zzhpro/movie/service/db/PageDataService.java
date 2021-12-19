package com.zzhpro.movie.service.db;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.dao.FilmRepository;
import com.zzhpro.movie.entity.Film;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhihao zhang
 * @since 4/16/20
 */

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PageDataService {
    private FilmRepository filmRepository;

    static Specification<Film> titleContains(String title) {
        return (film, cq, cb) -> cb.like(film.get("title"), "%" + title + "%");
    }

    static Specification<Film> summaryContains(String summary) {
        return (film, cq, cb) -> cb.like(film.get("summary"), "%" + summary + "%");
    }

    public Page<Film> getFilmPageByMovieTypeEnum(MovieTypeEnum movieTypeEnum,
                                                 Pageable pageable) {
        return filmRepository.findAllByMovieTypeEnum(movieTypeEnum, pageable);
    }

    public Page<Film> getFilmBySearchText(String searchText, Pageable pageable) {
        Map<String, String> filterMap = ImmutableMap
                .of("title", searchText, "summary", searchText);
        List<Specification<Film>> specificationList = Lists.newArrayList();
        filterMap.forEach((key, value) ->
                specificationList.add((film, cq, cb) -> cb.like(film.get(key), "%" + value + "%")));
        Specification<Film> specification = null;
        if (!specificationList.isEmpty()) {
            specification = Specification.where(specificationList.get(0));
        }

        for (int i = 1; i < specificationList.size(); i++) {
            if (Objects.nonNull(specification)) {
                specification = specification.or(specificationList.get(i));
            } else {
                specification = Specification.where(specificationList.get(i));
            }
        }

        return filmRepository.findAll(specification, pageable);
    }
}
