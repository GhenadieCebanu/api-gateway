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

//        .route(r -> r.path("/get-out/health")
//            .filters(f -> f.rewritePath("/get-out/health", "/event-management/health"))
//            .uri(serviceDiscoveryProperties.eventsServiceUri))
//        .route(r -> r.path("/get-out/info")
//            .filters(f -> f.rewritePath("/get-out/info", "/event-management/info"))
//            .uri(serviceDiscoveryProperties.eventsServiceUri))
//        .route(r -> r.path("/get-out/**")
//            .filters(f -> f.rewritePath("/get-out/(?<segment>.*)", "/event-management/${segment}"))
//            .uri(serviceDiscoveryProperties.eventsServiceUri))

        .build();
  }
}
