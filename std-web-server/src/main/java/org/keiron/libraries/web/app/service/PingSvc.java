package org.keiron.libraries.web.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PingSvc {

  public Mono<Long> ping(long at) {
    return Mono.defer(() -> {
      var delayMillis = ThreadLocalRandom.current().nextLong(100, 200);
      return Mono
          .delay(Duration.of(delayMillis, ChronoUnit.MILLIS))
          .map(ignored -> Instant.now().toEpochMilli() - at);
    });
  }

}
