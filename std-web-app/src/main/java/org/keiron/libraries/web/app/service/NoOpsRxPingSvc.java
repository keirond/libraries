package org.keiron.libraries.web.app.service;

import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NoOpsRxPingSvc implements RxPingSvc {

  @Override
  public Mono<PingRes> ping(PingReq command) {
    return null;
  }

}
