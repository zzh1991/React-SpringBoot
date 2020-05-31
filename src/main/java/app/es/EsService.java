package app.es;

import app.constant.MovieTypeEnum;
import app.entity.Film;
import app.service.MovieService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Zhihao Zhang
 * @since 2019-03-12
 */

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EsService {
    private MovieService movieService;
    private EsFilmRepository esFilmRepository;
    private RestHighLevelClient restHighLevelClient;

    public void saveAllMoviesToEs() {
        List<Film> filmList = movieService.getAllMovies();
        saveFilmsToEs(filmList);
    }

    public void saveMoviesToEsByMovieTypeEnum(MovieTypeEnum movieTypeEnum) {
        List<Film> filmList = movieService.getMoviesByMovieTypeEnum(movieTypeEnum);
        saveFilmsToEs(filmList);
    }

    private void saveFilmsToEs(List<Film> filmList) {
        List<EsFilm> esFilmList = Lists.newArrayList();
        filmList.forEach(film -> {
            EsFilm esFilm = EsFilm.builder().build();
            BeanUtils.copyProperties(film, esFilm);
            esFilmList.add(esFilm);
        });
        esFilmRepository.saveAll(esFilmList);
    }

    public void deleteMovieIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("movie");
        restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
    }

    public List<EsFilm> searchMovie(String search, MovieTypeEnum movieTypeEnum) {
        if (Strings.isNullOrEmpty(search)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList();
    }

    public List<EsFilm> searchMovieFuzzily(String search) {
        if (Strings.isNullOrEmpty(search)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList();
    }
}
