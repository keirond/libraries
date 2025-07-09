package org.keiron.libraries.web.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PingSvc {

  public Mono<Long> ping(long at) {
    return Mono
        .fromSupplier(() -> Instant.now().toEpochMilli() - at)
        .delaySubscription(Mono.fromSupplier(() -> ThreadLocalRandom.current().nextLong(100, 200)));
  }

}
