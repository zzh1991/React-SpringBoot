//package app.controller;
//
//import app.entity.Film;
//import app.service.MovieService;
//import app.service.db.DataService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.PostConstruct;
//
//import static io.restassured.RestAssured.get;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class MovieApplicationTest {
//
//    @LocalServerPort
//    private int port;
//
//    private String uri;
//
//    @MockBean
//    MovieService movieService;
//
//    @MockBean
//    DataService dataService;
//
//    @PostConstruct
//    public void init() {
//        uri = "http://localhost:" + port;
//    }
//
//    @Test
//    public void getFilmListById() {
//        Film film = Film.builder()
//                .id(1L)
//                .movieId(1L)
//                .build();
//
//        when(dataService.findByMovieId(1L)).thenReturn(film);
//        when(movieService.getMovieById(1L)).thenReturn(film);
//
//        Film result = get(uri.concat("/list/").concat(film.getMovieId().toString()))
//        .then()
//        .assertThat()
//        .statusCode(HttpStatus.OK.value())
//        .extract()
//        .as(Film.class);
//        assertThat(result).isEqualTo(film);
//    }
//}