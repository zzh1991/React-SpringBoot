package app.dao;

import app.entity.TopFilm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopFilmRepository extends CrudRepository<TopFilm, Long> {
    List<TopFilm> findByCurrentIsTrueOrderByRatingDesc();

    TopFilm findFirstByMovieIdOrderByIdDesc(Long id);
}
