package app.graphql;

import app.constant.MovieTypeEnum;
import app.dao.FilmListRepository;
import app.entity.FilmList;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhihao zhang
 * @date 2019-04-14
 */

@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    private FilmListRepository filmListRepository;

    public List<FilmList> getFilmLists() {
        return filmListRepository.findAllByOrderByMovieYearDescRatingDesc();
    }

    public List<FilmList> getFilmListsByMovieType(MovieTypeEnum movieTypeEnum) {
        return filmListRepository.findByMovieTypeEnumOrderByRatingDesc(movieTypeEnum);
    }
}
