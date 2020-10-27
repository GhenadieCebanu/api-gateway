package api.gateway.config;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import api.gateway.ApiGatewayApplication;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("integration-test")
@SpringBootTest(classes = ApiGatewayApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class RoutesConfigITest {

  @Autowired
  private WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    setDefaultWireMock();
  }

  @Test
  void shouldHitEventsEndpoint() {
    // Given
    stubFor(get("/v1/get-out/events").willReturn(ok("Found")));

    // When, Then
    webTestClient.get().uri("/v1/get-out/events").exchange()
        .expectStatus().isOk();
  }

  @Test
  void shouldTranslatePostToGet() {
    // Given
    stubFor(get("/v1/get-out/users/1").willReturn(ok("Found")));

    // When, Then
    webTestClient.post().uri("/v1/get-out/users")
        .contentType(APPLICATION_JSON).bodyValue(Map.of("id","1"))
        .exchange().expectStatus().isOk();
  }

  public static void setDefaultWireMock() {
    stubFor(any(anyUrl()).atPriority(10).willReturn(aResponse().withStatus(404)
        .withBody("{\"status\":\"Error\",\"message\":\"Endpoint not found\"}")));
  }

}
