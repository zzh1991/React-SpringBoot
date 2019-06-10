package app.mapper;

import app.constant.MovieTypeEnum;
import app.entity.FilmList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FilmListMapper {

    @Select("select * from movie_list order by movie_year desc, rating desc")
    List<FilmList> findAllByOrderByMovieYearDescRatingDesc();

    @Select("select * from movie_list where movie_type = #{movieTypeEnum} order by rating desc")
    List<FilmList> findByMovieTypeEnumOrderByRatingDesc(@Param("movieTypeEnum") MovieTypeEnum movieTypeEnum);

}
