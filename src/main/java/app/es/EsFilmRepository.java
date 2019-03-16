package app.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zhihao Zhang
 * @date 2019-03-12
 */

@Repository
public interface EsFilmRepository extends ElasticsearchRepository<EsFilm, Long> {
}
