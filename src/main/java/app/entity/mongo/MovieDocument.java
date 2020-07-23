package app.entity.mongo;

import app.constant.MovieTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zzh
 */

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Document("movie")
public class MovieDocument {
    @Id
    private long movieId;

    private String title;
    private double rating;
    private int movieYear;
    private String url;
    private String largeImageUrl;
    @Field("casts")
    private List<String> castList;
    @Field("directors")
    private List<String> directorList;
    @Field("genres")
    private List<String> genreList;
    @Field("countries")
    private List<String> countryList;
    private String summary;

    private LocalDateTime updateTime;

    @Field("movie_type")
    @Enumerated(value = EnumType.STRING)
    @Indexed
    private MovieTypeEnum movieTypeEnum;

}
