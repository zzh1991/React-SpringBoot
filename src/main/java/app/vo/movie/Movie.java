package app.vo.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Movie {
    private Long id;
    private Rating rating;
    private List<String> genres;
    private String title;

    @JsonProperty("collect_count")
    private String collectCount;

    private String alt;
    private int year;

    private Map<String, String> images;
    private List<Avatar> casts;
    private List<Avatar> directors;
}
