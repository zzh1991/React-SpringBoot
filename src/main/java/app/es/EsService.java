package app.es;

import app.entity.FilmList;
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

    public List<EsFilm> searchMovie(String search) {
        if (Strings.isNullOrEmpty(search)) {
            return Lists.newArrayList();
        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(search)
                        .field("title")
                        .field("casts")
                        .field("summary")
//                        .slop(1)
//                        .fuzziness(Fuzziness.ONE)
//                        .prefixLength(5)
                        .minimumShouldMatch("90%")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, EsFilm.class);
    }
}
