package app.service.db;

import app.constant.MovieTypeEnum;
import app.dao.FilmRepository;
import app.entity.Film;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    static Specification<Film> titleContains(String title) {
        return (book, cq, cb) -> cb.like(book.get("title"), "%" + title + "%");
    }

    static Specification<Film> summaryContains(String summary) {
        return (book, cq, cb) -> cb.like(book.get("summary"), "%" + summary + "%");
    }

    public Page<Film> getFilmPageByMovieTypeEnum(MovieTypeEnum movieTypeEnum,
                                                 Pageable pageable) {
        return filmRepository.findAllByMovieTypeEnum(movieTypeEnum, pageable);
    }

    public Page<Film> getFilmBySearchText(String searchText, Pageable pageable) {
        return filmRepository.findAll(Specification.where(titleContains(searchText))
                .or(summaryContains(searchText)), pageable);
    }
}
