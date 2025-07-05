package org.keiron.libraries.web.app.controller;

import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.service.StdRxPingSvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/rx/ping")
@RequiredArgsConstructor
public class RxPingHttpCtrl {

  private final StdRxPingSvc rxPingSvc;

  @PostMapping(path = "/v1", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public Mono<ResponseEntity<?>> ping(@RequestBody PingReq command) {
    return rxPingSvc.ping(command).map(ResponseEntity::ok);
  }

}
