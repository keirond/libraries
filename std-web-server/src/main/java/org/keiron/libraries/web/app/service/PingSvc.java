package org.keiron.libraries.web.app.service;

import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PongRes;
import reactor.core.publisher.Mono;

interface PingSvc {

  Mono<PongRes> ping(PingReq command);

}
