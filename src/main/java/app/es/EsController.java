package app.es;

import app.constant.MovieTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zhihao Zhang
 * @date 2019-03-12
 */

@RestController
@RequestMapping("/es/")
public class EsController {
    @Autowired
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
    public void deleteMovieIndex() {
        esService.deleteMovieIndex();
    }

    @GetMapping("movie/{search}")
    public List<EsFilm> searchMovie(@PathVariable String search) {
        return esService.searchMovie(search);
    }

    @GetMapping("movie/fuzzy/{search}")
    public List<EsFilm> searchMovieFuzzily(@PathVariable String search) {
        return esService.searchMovieFuzzily(search);
    }
}
