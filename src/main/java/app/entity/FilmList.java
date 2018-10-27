package app.entity;

import app.constant.MovieTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhihao zhang
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_list")
public class FilmList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    private Boolean viewed;
    private Boolean star;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "movie_type")
    private MovieTypeEnum movieTypeEnum;
}
