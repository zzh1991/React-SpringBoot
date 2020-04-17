package app.dao;

import app.constant.MovieTypeEnum;
import app.entity.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhihao zhang
 * @since 2019.06.10
 */

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
    /**
     * find film by movie id
     * @param movieId movieId
     * @return Film
     */
    Film findFirstByMovieId(Long movieId);

    /**
     * get viewed or stared films by movie ids
     *
     * @param ids list of movieIds
     * @return list of Film
     */
    List<Film> findByMovieIdIsIn(List<Long> ids);

    /**
     * get all movies
     * @return list of Film
     */
    List<Film> findAllByOrderByMovieYearDescRatingDesc();

    /**
     * get movie list by movie type
     * @param movieTypeEnum movie type enum
     * @return list of Film
     */
    List<Film> findByMovieTypeEnumOrderByRatingDesc(MovieTypeEnum movieTypeEnum);

    /**
     * get movie page by movie type
     * @param movieTypeEnum movie type enum
     * @param pageable pageable
     * @return page of Film
     */
    Page<Film> findAllByMovieTypeEnum(MovieTypeEnum movieTypeEnum, Pageable pageable);
}
