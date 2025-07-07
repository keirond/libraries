package org.keiron.libraries.web.app.monitor;

import io.micrometer.core.instrument.Tags;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StdMonitor implements Monitor {

  private final PrometheusMeterRegistry registry;

  @Override
  public Mono<Void> counter(String metricId, Tags tags) {
    return Mono.fromRunnable(() -> registry.counter(metricId, tags).increment());
  }

  public Mono<Void> gauge() {
    return Mono.empty();
  }

}
