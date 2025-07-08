package org.keiron.libraries.web.app.controller;

import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.service.PingSvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/v1/ping", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PingHttpCtrl {

  private final PingSvc pingSvc;

  @PostMapping(path = "")
  public Mono<ResponseEntity<?>> pingRx(@RequestBody PingReq command) {
    return pingSvc.ping(command).map(ResponseEntity::ok);
  }

}
