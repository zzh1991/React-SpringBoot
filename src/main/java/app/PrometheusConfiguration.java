package app;

import io.prometheus.client.CollectorRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Prometheus Configuration
 * @author Zhihao Zhang
 * @date 2018.8.30
 */

@Configuration
@ConditionalOnClass(CollectorRegistry.class)
public class PrometheusConfiguration {
    @Bean
    @ConditionalOnMissingBean
    CollectorRegistry metricRegistry() {
        return CollectorRegistry.defaultRegistry;
    }
}
