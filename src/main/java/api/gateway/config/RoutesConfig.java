package api.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

  @Bean
  public RouteLocator gatewayRoutes(final RouteLocatorBuilder builder,
      final ServiceDiscoveryProperties serviceDiscoveryProperties) {
    return builder.routes()
        .route(r -> r.path("/v1/get-out/users/**").uri(serviceDiscoveryProperties.userManagementServiceUri))
        .route(r -> r.path("/v1/get-out/events").uri(serviceDiscoveryProperties.eventsServiceUri))
        .build();
  }
}
