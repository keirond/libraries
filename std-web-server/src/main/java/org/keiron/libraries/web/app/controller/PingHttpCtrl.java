package org.keiron.libraries.web.app.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.service.PingSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping(path = "/v1/ping")
@RequiredArgsConstructor
public class PingHttpCtrl {

  private final PingSvc pingSvc;

  @GetMapping
  public Mono<ResponseEntity<?>> ping(@RequestParam @Min(1) long at) {
    return pingSvc.ping(at).map(ResponseEntity::ok);
  }

}
