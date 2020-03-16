package api.gateway.config;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import java.time.Duration;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerConfig {

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCircuitBreakerCustomizer(
      final CircuitBreakerProperties circuitBreakerProperties) {

    return factory -> factory
        .configureDefault(id -> new Resilience4JConfigBuilder(id)
            .circuitBreakerConfig(
                io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.ofDefaults())
            .timeLimiterConfig(TimeLimiterConfig.custom()
                .timeoutDuration(
                    Duration.ofSeconds(circuitBreakerProperties.timeoutDurationInSeconds))
                .build())
            .build());
  }
}
