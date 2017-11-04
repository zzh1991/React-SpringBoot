package app.dao;

import app.entity.FilmList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmListRepository extends CrudRepository<FilmList, Long> {
    List<FilmList> findByOrderByRatingDesc();

    FilmList findFirstByMovieId(Long movieId);

    List<FilmList> findByMovieIdIsIn(List<Long> ids);
}
