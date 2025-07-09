package org.keiron.libraries.web.app.controller;

import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.service.PingSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/v1/ping")
@RequiredArgsConstructor
public class PingHttpCtrl {

  private final PingSvc pingSvc;

  @GetMapping
  public Mono<ResponseEntity<?>> pingRx(@RequestParam long at) {
    return pingSvc.ping(at).map(ResponseEntity::ok);
  }

}
