package app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_top")
public class TopFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long movieid;

    private String title;
    private double rating;
    private int movie_year;
    private String url;
    private String image_large;
    private String casts;
    private String directors;
    private String genres;
    private String summary;
    private String countries;
    private boolean current;
    private boolean viewed;
}
