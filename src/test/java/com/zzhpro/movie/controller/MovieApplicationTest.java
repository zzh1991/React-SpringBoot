package com.zzhpro.movie.controller;

import com.zzhpro.movie.entity.Film;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MovieApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getFilmListById() throws Exception {
        Film film = Film.builder()
                .id(1L)
                .movieId(1L)
                .build();

//        mvc.perform(get("/list/".concat(film.getMovieId().toString())))
//                .andExpect(status().isOk());

    }
}