package app;

import com.google.common.collect.Maps;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

/**
 * @author zhihao zhang
 * @since 9/7/20
 */

@Configuration
public class KafkaTopicConfig {
    private String bootstrapAddress = "localhost:9092";

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = Maps.newHashMap();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic movieTopic() {
        return TopicBuilder.name("movie").partitions(2).build();
//        return new NewTopic("movie", 1, (short) 1);
    }

    @Bean
    public NewTopic movieObjectTopic() {
        return TopicBuilder.name("film").partitions(2).build();
    }
}
