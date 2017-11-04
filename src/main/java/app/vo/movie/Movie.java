package app.vo.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long id;
    private Rating rating;
    private List<String> genres;
    private String title;
    private String collect_count;
    private String original_title;
    private String subtype;
    private String alt;
    private int year;

    private Map<String, String> images;
    private List<Avatar> casts;
    private List<Avatar> directors;
}
