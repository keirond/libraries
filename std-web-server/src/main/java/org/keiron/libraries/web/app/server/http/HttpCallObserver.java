package org.keiron.libraries.web.app.server.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpCallObserver implements WebFilter {

  @Override
  public @NonNull Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    var startTime = Instant.now();
    var request = exchange.getRequest();
    var headers = request.getHeaders();
    var method = request.getMethod();
    var uri = request.getURI();
    var url = Optional.ofNullable(uri.getPath()).orElse("") +
        Optional.ofNullable(uri.getQuery()).map("?"::concat).orElse("");

    // TODO
    var correlationId = headers
        .getOrDefault("correlation-id", Collections.singletonList(UUID.randomUUID().toString()))
        .getFirst();

    // TODO
    var requests = new ArrayList<String>();
    var responses = new ArrayList<String>();

    return chain.filter(exchange).doOnSuccess(unused -> {
      var response = exchange.getResponse();
      var statusCode = Optional
          .ofNullable(response.getStatusCode())
          .map(HttpStatusCode::value)
          .orElse(-1);

      log.info(
          "Closed http.request with method: {}, url: {}, response.status_code: {}, duration: {}ms, and requests: {}, responses: {}",
          method, url, statusCode, Duration.between(startTime, Instant.now()).toMillis(), requests,
          responses);
    });
  }

}
