package app.dao;

import app.entity.Film;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhihao zhang
 */
public interface FilmRepository extends CrudRepository<Film, Long> {
    /**
     * find all newest films
     *
     * @return List<Film>
     */
    List<Film> findByCurrentIsTrueOrderByRatingDesc();

    /**
     * find a film by id
     *
     * @param id movieId
     * @return Film
     */
    Film findFirstByMovieIdOrderByIdDesc(Long id);
}
