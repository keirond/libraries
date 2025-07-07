package org.keiron.libraries.web.app.monitor;

import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StdMonitor implements Monitor {

  private final PrometheusMeterRegistry registry;

  @Override
  public Mono<Void> counter() {
    return null;
  }

}
