package app;

import app.service.MovieService;
import app.service.db.DataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringTestConfiguration {
    @Bean
    public MovieService movieServiceTest(DataService dataService) {
        return new MovieService(dataService);
    }
}
