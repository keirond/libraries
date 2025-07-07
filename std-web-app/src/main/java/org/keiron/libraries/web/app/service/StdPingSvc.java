package org.keiron.libraries.web.app.service;

import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class StdPingSvc implements PingSvc {

  public PingRes ping(PingReq command) {
    long delayMillis = ThreadLocalRandom.current().nextLong(100, 200);
    try {
      Thread.sleep(delayMillis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted during delay", e);
    }
    return new PingRes().setReqEpochMillis(command.getReqEpochMillis())
               .setResEpochMillis(Instant.now().toEpochMilli());
  }

  public Mono<PingRes> pingRx(PingReq command) {
    long delayMillis = ThreadLocalRandom.current().nextLong(100, 200);
    return Mono.delay(Duration.ofMillis(delayMillis)).map(
        ignore -> new PingRes().setReqEpochMillis(command.getReqEpochMillis())
                      .setResEpochMillis(Instant.now().toEpochMilli()));
  }

}
