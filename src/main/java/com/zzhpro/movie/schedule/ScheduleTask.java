package com.zzhpro.movie.schedule;

import com.zzhpro.movie.constant.MovieTypeEnum;
import com.zzhpro.movie.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author zhihao zhang
 * @since 2018.4.27
 */

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleTask {
    private static final String ZONE = "GMT+8";

    private MovieService movieService;

    @Scheduled(cron = "0 0 22 * * ?", zone = ZONE)
    public void updateMovie() {
        movieService.syncMovies(MovieTypeEnum.RECENT);
        log.info("update movie successfully");
    }

    @Scheduled(cron = "0 30 22 * * ?", zone = ZONE)
    public void updateTopMovie() {
        movieService.syncMovies(MovieTypeEnum.TOP);
        log.info("update top movie successfully");
    }
}
