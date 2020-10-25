package api.gateway.config;

import java.time.Duration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class RoutesConfig {

  @Bean
  public RouteLocator gatewayRoutes(final RouteLocatorBuilder builder,
      final ServiceDiscoveryProperties serviceDiscoveryProperties) {
    return builder.routes()
        .route(r -> r.path("/v1/get-out/events")
            .filters(f -> f
                .circuitBreaker(c -> {
                  c.setFallbackUri("forward:/fallback/v1/get-out/events");
                })
                .addRequestHeader("view", "legacy")
                .addResponseHeader("response-service", "event-management")
            )
            .uri(serviceDiscoveryProperties.eventsServiceUri))

        .route(r -> r.path("/v1/get-out/users")
            .filters(f -> f
                .retry(retryConfig -> {
                  retryConfig.setRetries(3);
                  retryConfig.setBackoff(Duration.ofSeconds(2), Duration.ofSeconds(16), 2, true);
                  retryConfig.setStatuses(HttpStatus.GATEWAY_TIMEOUT, HttpStatus.REQUEST_TIMEOUT,
                      HttpStatus.BAD_REQUEST, HttpStatus.NOT_FOUND);
                })
                .addResponseHeader("response-service", "user-management")
            )
            .uri(serviceDiscoveryProperties.userManagementServiceUri))

//        .route(r -> r.path("/get-out/**")
//            .filters(f -> f
//                .rewritePath("/get-out/(?<segment>.*)", "/event-management/${segment}")
//                .addResponseHeader("response-service", "event-management"))
//            .uri(serviceDiscoveryProperties.eventsServiceUri))

        .route(r -> r.path("/fallback/v1/get-out/**")
            .filters(f -> f
                .rewritePath("/fallback/(?<segment>.*)", "/${segment}")
                .addResponseHeader("response-service", "monolit-app"))
            .uri(serviceDiscoveryProperties.fallbackUri))
        .build();
  }

}
