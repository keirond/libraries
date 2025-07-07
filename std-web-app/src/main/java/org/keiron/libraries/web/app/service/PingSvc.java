package org.keiron.libraries.web.app.service;

import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import reactor.core.publisher.Mono;

interface PingSvc {

  PingRes ping(PingReq command);

  Mono<PingRes> pingRx(PingReq command);

}
