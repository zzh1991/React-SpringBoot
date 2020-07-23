package app.controller.mongo;

import app.constant.MovieTypeEnum;
import app.entity.mongo.MovieDocument;
import app.service.db.mongo.MongoDataService;
import app.transform.FilmToMovieDocumentTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zzh
 */
@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("mongo/")
public class MongoController {
    private MongoDataService mongoDataService;
    private FilmToMovieDocumentTransformer filmToMovieDocumentTransformer;

    @PutMapping("movie/{movieType}")
    public List<MovieDocument> saveMovieList(@PathVariable MovieTypeEnum movieType) {
        return filmToMovieDocumentTransformer.transform(movieType);
    }

    @GetMapping("movie/{movieType}")
    public List<MovieDocument> getMovieList(@PathVariable MovieTypeEnum movieType) {
        return mongoDataService.getMovieListByMovieType(movieType);
    }
}
