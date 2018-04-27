package app.schedule;

import app.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleTask {

    private static final String ZONE = "GMT+8";
    private MovieService movieService;

    @Scheduled(cron = "0 0 22 * * ?", zone = ZONE)
    public void updateMovie() {
        try {
            movieService.syncRecentMovies();
            log.info("update movie successfully");
        } catch (Exception e) {
            log.error("update movie failed: {}", e.getMessage());
        }
    }

    @Scheduled(cron = "0 30 22 * * ?", zone = ZONE)
    public void updateTopMovie() {
        try {
            movieService.syncTopMovies();
            log.info("update top movie successfully");
        } catch (Exception e) {
            log.error("update top movie failed: {}", e.getMessage());
        }
    }
}
