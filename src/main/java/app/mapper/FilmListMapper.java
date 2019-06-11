package app.mapper;

import app.constant.MovieTypeEnum;
import app.entity.FilmList;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilmListMapper {

    @Select("select * from movie_list order by movie_year desc, rating desc")
    @Results({
            @Result(property = "movieTypeEnum", column = "movie_type"),
    })
    List<FilmList> findAllByOrderByMovieYearDescRatingDesc();

    @Select("select * from movie_list where movie_type = #{movieTypeEnum} order by rating desc")
    @Results({
            @Result(property = "movieTypeEnum", column = "movie_type"),
    })
    List<FilmList> findByMovieTypeEnumOrderByRatingDesc(@Param("movieTypeEnum") MovieTypeEnum movieTypeEnum);

}
