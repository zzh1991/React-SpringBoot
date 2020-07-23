package app.transform;

import app.constant.MovieTypeEnum;
import app.entity.Film;
import app.entity.mongo.MovieDocument;
import app.service.db.DataService;
import app.service.db.mongo.MongoDataService;
import app.util.ConstantUtils;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzh
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FilmToMovieDocumentTransformer {
    private DataService dataService;
    private MongoDataService mongoDataService;

    public List<MovieDocument> transform(MovieTypeEnum movieTypeEnum) {
        List<Film> filmList = dataService.listFilmsByMovieTypeEnum(movieTypeEnum);
        List<MovieDocument> movieDocumentList = filmList.stream()
                .map(film -> MovieDocument.builder()
                        .movieId(film.getMovieId())
                        .title(film.getTitle())
                        .movieYear(film.getMovieYear())
                        .rating(film.getRating())
                        .url(film.getUrl())
                        .largeImageUrl(film.getImageLarge())
                        .castList(transformStringToList(film.getCasts()))
                        .directorList(transformStringToList(film.getDirectors()))
                        .genreList(transformStringToList(film.getGenres()))
                        .countryList(transformStringToList(film.getCountries()))
                        .movieTypeEnum(film.getMovieTypeEnum())
                        .updateTime(film.getUpdateTime())
                        .summary(film.getSummary())
                        .build())
                .collect(Collectors.toList());
        return mongoDataService.saveList(movieDocumentList);
    }

    private List<String> transformStringToList(String text) {
        if (StringUtils.isEmpty(text)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(text.split(ConstantUtils.SEPARATOR));
    }
}
