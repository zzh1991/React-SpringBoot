package app.controller;

import app.entity.Film;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;

import static io.restassured.RestAssured.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieApplicationTest {

    @LocalServerPort
    private int port;
    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Test
    public void getFilmListById() {
        Film film = Film.builder()
                .id(1L)
                .movieId(1L)
                .build();

        get(uri.concat("/list/").concat(film.getMovieId().toString()))
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value());
    }
}