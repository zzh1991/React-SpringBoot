package app.dao;

import app.entity.ViewFilm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ViewFilmRepository extends CrudRepository<ViewFilm, Long> {
    ViewFilm findOneByMovieid(Long id);

    List<ViewFilm> findAll();
}
