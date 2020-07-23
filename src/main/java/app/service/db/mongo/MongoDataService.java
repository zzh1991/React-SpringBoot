package app.service.db.mongo;

import app.constant.MovieTypeEnum;
import app.dao.mongo.MovieDocumentRepository;
import app.entity.mongo.MovieDocument;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zzh
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MongoDataService {
    private MovieDocumentRepository movieDocumentRepository;

    public List<MovieDocument> saveList(List<MovieDocument> movieDocumentList) {
        return movieDocumentRepository.saveAll(movieDocumentList);
    }

    public List<MovieDocument> getMovieListByMovieType(MovieTypeEnum movieTypeEnum) {
        return movieDocumentRepository.findByMovieTypeEnum(movieTypeEnum);
    }

}
