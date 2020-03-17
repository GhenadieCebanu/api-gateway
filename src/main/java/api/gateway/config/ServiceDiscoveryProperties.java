package api.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "service.discovery")
public class ServiceDiscoveryProperties {

  public final String userManagementServiceUri;
  public final String eventsServiceUri;
  public final String fallbackUri;

}
