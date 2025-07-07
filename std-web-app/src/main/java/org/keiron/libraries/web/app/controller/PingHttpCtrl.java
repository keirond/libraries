package org.keiron.libraries.web.app.controller;

import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import org.keiron.libraries.web.app.service.StdPingSvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/ping")
@RequiredArgsConstructor
public class PingHttpCtrl {

  private final StdPingSvc pingSvc;

  @PostMapping(path = "/v1", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<?> ping(@RequestBody PingReq command) {
    PingRes result = pingSvc.ping(command);
    return ResponseEntity.ok(result);
  }

  @PostMapping(path = "/rx/v1", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public Mono<ResponseEntity<?>> pingRx(@RequestBody PingReq command) {
    return pingSvc.pingRx(command).map(ResponseEntity::ok);
  }

}
