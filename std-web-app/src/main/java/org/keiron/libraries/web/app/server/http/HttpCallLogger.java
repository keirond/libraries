package org.keiron.libraries.web.app.server.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
public class HttpCallLogger implements WebFilter {

  @Override
  public @NonNull Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    var request = exchange.getRequest();
    var method = request.getMethod();
    var uri = request.getURI();
    var path = uri.getPath();
    var query = uri.getQuery();
    var url = Optional.ofNullable(path).orElse("") +
                  Optional.ofNullable(query).map("?"::concat).orElse("");
    log.info("Incoming http.reqeust with method: {}, url: {}", method, url);

    return chain.filter(exchange).doOnSuccess(unused -> {
      var response = exchange.getResponse();
      var statusCode = Optional.ofNullable(response.getStatusCode()).map(HttpStatusCode::value)
                           .orElse(-1);
      log.info("Handled http.reqeust with method: {}, url: {}, and response.status_code: {}",
          method, url, statusCode);
    });
  }

}
