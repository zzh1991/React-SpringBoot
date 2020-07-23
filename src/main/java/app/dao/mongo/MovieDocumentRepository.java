package app.dao.mongo;

import app.constant.MovieTypeEnum;
import app.entity.mongo.MovieDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zzh
 */
@Repository
public interface MovieDocumentRepository extends MongoRepository<MovieDocument, Long> {
    /**
     * get movie document by movie type enum
     * @param movieTypeEnum movie type enum
     * @return list of movie document
     */
    List<MovieDocument> findByMovieTypeEnum(MovieTypeEnum movieTypeEnum);
}
