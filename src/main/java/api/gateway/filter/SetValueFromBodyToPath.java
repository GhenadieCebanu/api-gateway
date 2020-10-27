package api.gateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.DefaultUriBuilderFactory;

@AllArgsConstructor
public class SetValueFromBodyToPath implements BodyToUriFunction {

  private final String basePath;

  @Override
  @SneakyThrows
  public URI apply(String body) {
    final ObjectMapper objectMapper = new ObjectMapper();
    final JsonNode root = objectMapper.readTree(body);
    final String idString = root.get("id").asText();
    if (StringUtils.isEmpty(idString)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Missing required field");
    }

    return new DefaultUriBuilderFactory().builder()
        .path(String.format("%s/%s", basePath, idString)).build();
  }
}
