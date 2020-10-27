package api.gateway.filter;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class BodyToUriFilterFactory extends AbstractGatewayFilterFactory<BodyToUriFunction> {

  public BodyToUriFilterFactory() {
    super(BodyToUriFunction.class);
  }

  @Override
  public GatewayFilter apply(final BodyToUriFunction bodyToUriRewriteFunction) {
    return (exchange, chain) -> ServerRequest
        .create(exchange, HandlerStrategies.withDefaults().messageReaders())
        .bodyToMono(String.class)
        .map(bodyToUriRewriteFunction)
        .map(uri -> {
          addOriginalRequestUrl(exchange, exchange.getRequest().getURI());
          return exchange.getRequest().mutate().uri(uri).build();
        })
        .flatMap(newRequest -> {
          exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
          return chain.filter(exchange.mutate().request(newRequest).build());
        });
  }

}
