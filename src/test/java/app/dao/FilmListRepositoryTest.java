//package app.dao;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import app.constant.MovieTypeEnum;
//import app.entity.FilmList;
//import com.google.common.collect.Lists;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class FilmListRepositoryTest {
//    @Autowired
//    private FilmListRepository filmListRepository;
//
//    @Transactional
//    @Test
//    @Sql(scripts = { "/test-film-data.sql" })
//    public void findFirstByMovieId() {
//        FilmList filmList = filmListRepository.findFirstByMovieId(1292052L);
//        assertThat(filmList.getMovieId()).isEqualTo(1292052L);
//    }
//
//    @Transactional
//    @Test
//    @Sql(scripts = { "/test-film-data.sql" })
//    public void findByMovieIdIsIn() {
//        List<FilmList> filmList = filmListRepository.findByMovieIdIsIn(Lists.newArrayList(1292052L));
//        assertThat(filmList.size()).isEqualTo(1);
//    }
//
//    @Transactional
//    @Test
//    @Sql(scripts = { "/test-film-data.sql" })
//    public void findAllByOrderByMovieYearDescRatingDesc() {
//        List<FilmList> filmList = filmListRepository.findAllByOrderByMovieYearDescRatingDesc();
//        assertThat(filmList.isEmpty()).isEqualTo(false);
//    }
//
//    @Transactional
//    @Test
//    @Sql(scripts = { "/test-film-data.sql" })
//    public void findByMovieTypeEnumOrderByRatingDesc() {
//        List<FilmList> filmList = filmListRepository.findByMovieTypeEnumOrderByRatingDesc(MovieTypeEnum.TOP);
//        assertThat(filmList.isEmpty()).isEqualTo(false);
//    }
//}