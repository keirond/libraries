package org.keiron.libraries.web.app.monitor;

import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class NoOpsMonitor implements Monitor {

  @Override
  public Mono<Void> counter(String metricId, Tags tags) {
    return Mono.empty();
  }

}
