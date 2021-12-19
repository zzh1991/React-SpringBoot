package com.zzhpro.movie.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zzhpro.movie.constant.MovieTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
