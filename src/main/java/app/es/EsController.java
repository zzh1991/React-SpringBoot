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

    @GetMapping("movie")
    public void saveAllMovieToEs() {
        esService.saveAllMoviesToEs();
    }

    @GetMapping("movie/recent")
    public void saveRecentMoviesToEs() {
        esService.saveMoviesToEsByMovieTypeEnum(MovieTypeEnum.RECENT);
    }

    @GetMapping("movie/top")
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
}
