package org.keiron.libraries.web.app.monitor;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class NoOpsMonitor implements Monitor {

  @Override
  public Mono<Void> counter() {
    return Mono.empty();
  }

}
