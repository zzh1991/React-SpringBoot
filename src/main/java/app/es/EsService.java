package app.es;

import app.constant.MovieTypeEnum;
import app.entity.Film;
import app.service.MovieService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.Operator.AND;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

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

    public void deleteMovieIndex() {
        elasticsearchTemplate.deleteIndex(EsFilm.class);
    }

    public List<EsFilm> searchMovie(String search) {
        if (Strings.isNullOrEmpty(search)) {
            return Lists.newArrayList();
        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(search)
                        .field("title")
                        .field("casts")
                        .field("directors")
                        .field("summary")
                        .minimumShouldMatch("100%")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, EsFilm.class);
    }

    public List<EsFilm> searchMovieFuzzily(String search) {
        if (Strings.isNullOrEmpty(search)) {
            return Lists.newArrayList();
        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(search)
                        .field("title")
                        .field("casts")
                        .field("directors")
                        .field("summary")
                        .operator(AND)
                        .slop(1)
                        .fuzziness(Fuzziness.ONE)
                        .maxExpansions(3)
                        .minimumShouldMatch("90%")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, EsFilm.class);
    }
}
