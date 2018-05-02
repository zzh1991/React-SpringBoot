package app.dao;

import app.entity.TopFilm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhihao zhang
 */
public interface TopFilmRepository extends CrudRepository<TopFilm, Long> {
    /**
     * find top 100 movies
     *
     * @return List<TopFilm>
     */
    List<TopFilm> findByCurrentIsTrueOrderByRatingDesc();

    /**
     * find top movie by movie id
     *
     * @param id movieId
     * @return TopFilm
     */
    TopFilm findFirstByMovieIdOrderByIdDesc(Long id);
}
