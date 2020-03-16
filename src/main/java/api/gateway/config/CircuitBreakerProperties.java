package api.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "circuit.breaker")
public class CircuitBreakerProperties {

  public final Integer timeoutDurationInSeconds;

}