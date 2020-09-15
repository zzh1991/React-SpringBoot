package app;

import app.entity.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author zhihao zhang
 * @since 9/12/20
 */

@Slf4j
@Component
public class Consumer {
    @KafkaListener(topics = "film", containerFactory = "movieKafkaListenerContainerFactory")
    public void listenFilmObject(Film film) {
        log.info("film: {}", film);
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = "movie", partitions = { "0" }))
    public void listenToPartition(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("message: {}, partition: {}, offset: {}", message, partition, offset);
    }
}
