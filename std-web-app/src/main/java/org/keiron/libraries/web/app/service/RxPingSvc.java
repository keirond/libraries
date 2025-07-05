package org.keiron.libraries.web.app.service;

import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import reactor.core.publisher.Mono;

interface RxPingSvc {

  Mono<PingRes> ping(PingReq command);

}
