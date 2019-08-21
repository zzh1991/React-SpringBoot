package app.dao;

import app.constant.MovieTypeEnum;
import app.entity.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhihao zhang
 */
@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {
    /**
     * find film by movie id
     * @param movieId movieId
     * @return FilmList
     */
    Film findFirstByMovieId(Long movieId);

    /**
     * get viewed or stared films by movie ids
     *
     * @param ids movieIds
     * @return List<FilmList>
     */
    List<Film> findByMovieIdIsIn(List<Long> ids);

    /**
     * get all movies
     * @return List<FilmList>
     */
    List<Film> findAllByOrderByMovieYearDescRatingDesc();

    /**
     * get movie list by movie type
     * @param movieTypeEnum
     * @return
     */
    List<Film> findByMovieTypeEnumOrderByRatingDesc(MovieTypeEnum movieTypeEnum);
}
