package app.vo.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author zhihao zhang
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieSubject {
    private Long id;
    private Rating rating;

    private List<String> genres;
    private String title;
    private int year;
    private String alt;
    private Map<String, String> images;
    private List<Avatar> casts;
    private List<Avatar> directors;


    private List<String> countries;
    private String summary;
}
