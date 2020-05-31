package app.es;

import app.constant.MovieTypeEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author Zhihao Zhang
 * @since 2019-03-12
 */

@RestController
@RequestMapping("/es/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EsController {
    private EsService esService;

    @PostMapping("movie")
    public void saveAllMovieToEs() {
        esService.saveAllMoviesToEs();
    }

    @PostMapping("movie/recent")
    public void saveRecentMoviesToEs() {
        esService.saveMoviesToEsByMovieTypeEnum(MovieTypeEnum.RECENT);
    }

    @PostMapping("movie/top")
    public void saveTopMoviesToEs() {
        esService.saveMoviesToEsByMovieTypeEnum(MovieTypeEnum.TOP);
    }

    @DeleteMapping("movie")
    public void deleteMovieIndex() throws IOException {
        esService.deleteMovieIndex();
    }

    @GetMapping("movie/{search}")
    public List<EsFilm> searchMovie(@PathVariable String search) {
        return esService.searchMovie(search, MovieTypeEnum.NORMAL);
    }

//    @GetMapping("movie/recent/{search}")
//    public List<EsFilm> searchRecentMovie(@PathVariable String search) {
//        return esService.searchMovie(search, MovieTypeEnum.RECENT);
//    }

    @GetMapping("movie/top/{search}")
    public List<EsFilm> searchTopMovie(@PathVariable String search) {
        return esService.searchMovie(search, MovieTypeEnum.TOP);
    }

    @GetMapping("movie/fuzzy/{search}")
    public List<EsFilm> searchMovieFuzzily(@PathVariable String search) {
        return esService.searchMovieFuzzily(search);
    }
}
