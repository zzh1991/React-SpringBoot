package com.zzhpro.movie.graphql;

import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.entity.Film;
import com.zzhpro.movie.service.db.DataService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhihao zhang
 * @since 2019-04-14
 */

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Query implements GraphQLQueryResolver {
    private DataService dataService;

    public List<Film> getFilms() {
        return dataService.listAllFilms();
    }

    public List<Film> getFilmsByMovieType(MovieTypeEnum movieTypeEnum) {
        return dataService.findByMovieTypeEnum(movieTypeEnum);
    }
}
