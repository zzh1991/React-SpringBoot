package app.es;

import app.constant.MovieTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Zhihao Zhang
 * @date 2019-03-12
 */

@AllArgsConstructor
@Builder
@Data
@Document(indexName = "movie", type = "movie")
@NoArgsConstructor
public class EsFilm {
    @Id
    private Long movieId;
    private String title;
    private double rating;
    private int movieYear;
    private String url;
    private String imageLarge;
    private String casts;
    private String directors;
    private String genres;
    private String summary;
    private String countries;
    @Enumerated(value = EnumType.STRING)
    private MovieTypeEnum movieTypeEnum;
}
