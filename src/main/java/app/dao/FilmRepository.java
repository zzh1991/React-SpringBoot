package app.dao;

import app.entity.Film;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmRepository extends CrudRepository<Film, Long> {
    List<Film> findByCurrentIsTrueOrderByRatingDesc();

    Film findOneByMovieId(Long id);
}
