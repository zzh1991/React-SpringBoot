package app.controller;

import app.dao.FilmListRepository;
import app.entity.FilmList;
import app.mapper.FilmListMapper;
import app.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieApplicationTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @MockBean
    MovieService movieService;

    @MockBean
    FilmListRepository filmListRepository;

    @MockBean
    FilmListMapper filmListMapper;

    @Test
    public void getFilmListById() {
        FilmList filmList = FilmList.builder()
                .id(1L)
                .movieId(1L)
                .build();

        when(filmListRepository.findFirstByMovieId(1L)).thenReturn(filmList);
        when(movieService.getFilmListById(1L)).thenReturn(filmList);

        FilmList result = get(uri.concat("/list/").concat(filmList.getMovieId().toString()))
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .as(FilmList.class);
        assertThat(result).isEqualTo(filmList);
    }
}