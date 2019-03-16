package app.es;

import app.entity.FilmList;
import app.service.MovieService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zhihao Zhang
 * @date 2019-03-12
 */

@Service
public class EsService {
    @Autowired
    private MovieService movieService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsFilmRepository esFilmRepository;

    public void saveMovieToEs() {
        List<FilmList> filmLists = movieService.getAllMoviesList();
        List<EsFilm> esFilmList = Lists.newArrayList();
        filmLists.forEach(filmList -> {
            EsFilm esFilm = EsFilm.builder().build();
            BeanUtils.copyProperties(filmList, esFilm);
            esFilmList.add(esFilm);
        });
        esFilmRepository.saveAll(esFilmList);
    }

    public void deleteMovieIndex() {
        elasticsearchTemplate.deleteIndex("movie");
    }
}
