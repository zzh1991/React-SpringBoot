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
@Table(name = "movie")
public class Film {
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
    private boolean current;
    private boolean viewed;
    private boolean star;
    private Long updateTime;
}
