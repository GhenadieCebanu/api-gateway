package api.gateway.filter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory.Config;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@AllArgsConstructor
public class HttpResponseLoggerFilter implements GlobalFilter, Ordered {

  private final ModifyResponseBodyGatewayFilterFactory filterFactory;

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }

  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
    log.info(LogUtil.formatResponseHeaders(exchange.getResponse()));
    final Config config = new Config();
    config.setInClass(byte[].class);
    config.setOutClass(byte[].class);
    config.setRewriteFunction((serverWebExchange, body) ->  Mono.just(body));
    return filterFactory.apply(config).filter(exchange, chain);
  }
}
