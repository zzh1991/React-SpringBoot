package com.zzhpro.movie.controller;

import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.entity.Film;
import com.zzhpro.movie.service.db.DataService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GraphqlController {
    private DataService dataService;

    @QueryMapping
    public List<Film> films() {
        return dataService.listAllFilms();
    }

    @QueryMapping
    public List<Film> filmsByMovieType(@Argument MovieTypeEnum movieTypeEnum) {
        return dataService.findByMovieTypeEnum(movieTypeEnum);
    }
}
