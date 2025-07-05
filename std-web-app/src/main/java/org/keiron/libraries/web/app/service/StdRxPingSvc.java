package org.keiron.libraries.web.app.service;

import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StdRxPingSvc implements RxPingSvc {

  @Override
  public Mono<PingRes> ping(PingReq command) {
    return Mono.just(new PingRes().setReqEpochMillis(command.getReqEpochMillis())
                         .setResEpochMillis(Instant.now().toEpochMilli()));
  }

}
