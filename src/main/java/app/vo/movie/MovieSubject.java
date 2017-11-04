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
public class MovieSubject {
    private Long id;
    private Rating rating;
    private Long reviews_count;
    private Long wish_count;
    private String douban_site;

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

    private String mobile_url;
    private String share_url;
    private String schedule_url;

    private Long do_count;
    private Long seasons_count;
    private Long episodes_count;
    private Long comments_count;
    private Long ratings_count;

    private List<String> countries;
    private String current_season;
    private String summary;
    List<String> aka;
}
