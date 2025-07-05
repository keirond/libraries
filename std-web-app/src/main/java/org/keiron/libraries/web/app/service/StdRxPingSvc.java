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
public class StdRxPingSvc implements RxPingSvc {

  @Override
  public Mono<PingRes> ping(PingReq command) {
    long delayMillis = ThreadLocalRandom.current().nextLong(100, 500);
    return Mono.delay(Duration.ofMillis(delayMillis)).map(
        ignore -> new PingRes().setReqEpochMillis(command.getReqEpochMillis())
                      .setResEpochMillis(Instant.now().toEpochMilli()));
  }

}
