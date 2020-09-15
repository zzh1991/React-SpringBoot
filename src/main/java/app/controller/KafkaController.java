package app.controller;

import app.constant.MovieTypeEnum;
import app.entity.Film;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhihao zhang
 * @since 9/7/20
 */

@RestController
@RequestMapping("/kafka/")
@Slf4j
public class KafkaController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Film> movieKafkaTemplate;

    @Autowired
    private NewTopic movieTopic;

    @PostMapping("produce")
    public void sendMessage(@RequestParam String message) {
        String topicName = movieTopic.name();
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }

    @PostMapping("send/movie")
    public void sendMovieObject() {
        String topicName = "film";
        Film film = Film.builder()
                .title("hello world")
                .rating(9.9)
                .movieTypeEnum(MovieTypeEnum.TOP)
                .build();
        movieKafkaTemplate.send(topicName, film);
    }
}
