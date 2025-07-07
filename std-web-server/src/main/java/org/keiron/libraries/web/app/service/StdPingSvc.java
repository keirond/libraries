package org.keiron.libraries.web.app.service;

import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PongRes;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class StdPingSvc implements PingSvc {

  public Mono<PongRes> ping(PingReq command) {
    long delayMillis = ThreadLocalRandom.current().nextLong(100, 200);
    return Mono.delay(Duration.ofMillis(delayMillis)).map(ignore -> new PongRes().setLatencyMillis(
        Instant.now().toEpochMilli() - command.getEpochMillis()));
  }

}
