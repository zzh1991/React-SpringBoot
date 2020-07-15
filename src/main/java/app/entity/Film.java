package app.entity;

import app.constant.MovieTypeEnum;
import app.vo.movie.Avatar;
import app.vo.movie.Movie;
import app.vo.movie.MovieSubject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static app.util.ConstantUtils.LARGE;
import static app.util.ConstantUtils.SEPARATOR;

/**
 * @author zhihao zhang
 * @since 2019.06.10
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_list")
@TableName("movie_list")
public class Film implements Serializable {
    private static final long serialVersionUID = -8398532270073465206L;

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

    @Builder.Default
    private Boolean viewed = Boolean.FALSE;
    @Builder.Default
    private Boolean star = Boolean.FALSE;

    private LocalDateTime updateTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "movie_type")
    @TableField("movie_type")
    private MovieTypeEnum movieTypeEnum;

    public static Film transformMovieToFilm(Movie movie, MovieTypeEnum movieTypeEnum) {
        return Film.builder()
                .movieId(movie.getId())
                .title(movie.getTitle())
                .rating(movie.getRating().getAverage())
                .url(movie.getAlt())
                .movieYear(movie.getYear())
                .imageLarge(movie.getImages().get(LARGE))
                .casts(Avatar.getNames(movie.getCasts()))
                .directors(Avatar.getNames(movie.getDirectors()))
                .genres(StringUtils.join(movie.getGenres(), SEPARATOR))
                .updateTime(LocalDateTime.now())
                .movieTypeEnum(movieTypeEnum)
                .build();
    }

    public static Film transformMovieAndOldFilmToNewFilm(Film newFilm, MovieTypeEnum movieTypeEnum,
                                                         Film oldFilm) {
        if (Objects.isNull(oldFilm)) {
            return newFilm;
        }
        newFilm.setGenres(oldFilm.getGenres());
        newFilm.setSummary(oldFilm.getSummary());

        if (MovieTypeEnum.TOP.equals(movieTypeEnum)) {
            newFilm.setDirectors(oldFilm.directors);
            newFilm.setCasts(oldFilm.casts);
            newFilm.setMovieYear(oldFilm.movieYear);
            newFilm.setRating(oldFilm.rating);
        }

        if (Objects.nonNull(oldFilm.getId())) {
            newFilm.setId(oldFilm.getId());
        }
        return newFilm;
    }

    public static Film transformMovieSubjectToFilm(MovieSubject movieSubject, MovieTypeEnum movieTypeEnum) {
        return Film.builder()
                .movieId(movieSubject.getId())
                .title(movieSubject.getTitle())
                .rating(movieSubject.getRating().getAverage())
                .url(movieSubject.getAlt())
                .movieYear(movieSubject.getYear())
                .imageLarge(movieSubject.getImages().get(LARGE))
                .casts(Avatar.getNames(movieSubject.getCasts()))
                .directors(Avatar.getNames(movieSubject.getDirectors()))
                .genres(StringUtils.join(movieSubject.getGenres(), SEPARATOR))
                .summary(movieSubject.getSummary())
                .countries(StringUtils.join(movieSubject.getCountries().toArray(), SEPARATOR))
                .movieTypeEnum(movieTypeEnum)
                .updateTime(LocalDateTime.now())
                .build();
    }
}
