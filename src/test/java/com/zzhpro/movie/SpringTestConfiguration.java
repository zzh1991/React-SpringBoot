package com.zzhpro.movie;

import com.zzhpro.movie.service.MovieService;
import com.zzhpro.movie.service.db.DataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringTestConfiguration {
    @Bean
    public MovieService movieServiceTest(DataService dataService) {
        return new MovieService(dataService);
    }
}
