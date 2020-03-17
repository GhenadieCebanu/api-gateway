package api.gateway.filter;

import org.springframework.http.HttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

public class LogUtil {

  public static String formatRequestHeaders(final HttpRequest request) {
    return String.format("Request: \nmethod: %s,\nURI: %s,\nheaders: %s",
        request.getMethod(), request.getURI(), request.getHeaders());
  }

  public static String formatResponseHeaders(final ServerHttpResponse response) {
    return String.format("Response: \nstatus code: %s,\nheaders: %s",
        response.getStatusCode(), response.getHeaders());
  }
}
