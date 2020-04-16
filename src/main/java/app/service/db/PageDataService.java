package app.service.db;

import app.constant.MovieTypeEnum;
import app.dao.FilmRepository;
import app.entity.Film;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author zhihao zhang
 * @since 4/16/20
 */

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PageDataService {
    private FilmRepository filmRepository;

    public Page<Film> getFilmPageByMovieTypeEnum(MovieTypeEnum movieTypeEnum,
                                                 Pageable pageable) {
        return filmRepository.findAllByMovieTypeEnum(movieTypeEnum, pageable);
    }
}
