package com.zzhpro.movie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zzhpro.movie.constant.MovieTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
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
    @Serial
    private static final long serialVersionUID = -8398532270073465206L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * movie id
     */
    private Long movieId;
    /**
     * movie title
     */
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

    public static Film transformMovieAndOldFilmToNewFilm(Film newFilm, Film oldFilm) {
        if (Objects.isNull(oldFilm)) {
            return newFilm;
        }

        newFilm.setGenres(oldFilm.getGenres());
        newFilm.setSummary(oldFilm.getSummary());

        if (Objects.nonNull(oldFilm.getId())) {
            newFilm.setId(oldFilm.getId());
        }

        if (StringUtils.isNotBlank(oldFilm.getDirectors())) {
            newFilm.setDirectors(oldFilm.getDirectors());
        }

        if (StringUtils.isNotBlank(oldFilm.getCasts())) {
            newFilm.setCasts(oldFilm.getCasts());
        }

        if (oldFilm.getMovieYear() != 0) {
            newFilm.setMovieYear(oldFilm.getMovieYear());
        }

        if (!Objects.equals(0d, oldFilm.getRating())) {
            newFilm.setRating(oldFilm.getRating());
        }

        return newFilm;
    }

    private void transformMovieAndOldFilmToNewFilm(Film oldFilm) {
        if (Objects.isNull(oldFilm)) {
            return;
        }

        genres = oldFilm.getGenres();

        summary = oldFilm.getSummary();

        if (Objects.nonNull(oldFilm.getId())) {
            id = oldFilm.getId();
        }

        if (StringUtils.isNotBlank(oldFilm.getDirectors())) {
            directors = oldFilm.getDirectors();
        }

        if (StringUtils.isNotBlank(oldFilm.getCasts())) {
            casts = oldFilm.getCasts();
        }

        if (oldFilm.getMovieYear() != 0) {
            movieYear = oldFilm.getMovieYear();
        }

        if (!Objects.equals(0d, oldFilm.getRating())) {
            rating = oldFilm.getRating();
        }
    }
}
