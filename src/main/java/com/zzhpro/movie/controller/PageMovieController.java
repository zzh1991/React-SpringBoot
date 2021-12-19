package com.zzhpro.movie.controller;

import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.entity.Film;
import com.zzhpro.movie.service.db.PageDataService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhihao zhang
 * @since 4/16/20
 */

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("page/")
public class PageMovieController {
    private PageDataService pageDataService;

    @GetMapping("{movieType}")
    public Page<Film> getAllFilmPageByMovieType(@PathVariable String movieType, Pageable pageable) {
        MovieTypeEnum movieTypeEnum = MovieTypeEnum.valueOf(movieType.toUpperCase());

        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("rating").descending());
        return pageDataService.getFilmPageByMovieTypeEnum(movieTypeEnum, newPageable);
    }

    @GetMapping("search/{searchText}")
    public Page<Film> getAllFilmPageBySearchText(@PathVariable String searchText, Pageable pageable) {
        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("rating").descending());
        return pageDataService.getFilmBySearchText(searchText, newPageable);
    }
}
