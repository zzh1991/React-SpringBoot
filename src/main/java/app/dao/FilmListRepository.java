package app.dao;

import app.entity.FilmList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhihao zhang
 */
public interface FilmListRepository extends CrudRepository<FilmList, Long> {
    /**
     * find film by movie id
     * @param movieId movieId
     * @return FilmList
     */
    FilmList findFirstByMovieId(Long movieId);

    /**
     * get viewed or stared films by movie ids
     *
     * @param ids movieIds
     * @return List<FilmList>
     */
    List<FilmList> findByMovieIdIsIn(List<Long> ids);

    /**
     * get all movies
     * @return List<FilmList>
     */
    List<FilmList> findAllByOrderByMovieYearDescRatingDesc();
}
