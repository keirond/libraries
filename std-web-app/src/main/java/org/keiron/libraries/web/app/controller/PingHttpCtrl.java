package org.keiron.libraries.web.app.controller;

import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping(path = "/api/ping")
public class PingHttpCtrl {

  @PostMapping(path = "/v1", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<?> ping(@RequestBody PingReq command) {
    try {
      PingRes result = new PingRes().setReqEpochMillis(command.getReqEpochMillis())
                           .setResEpochMillis(Instant.now().toEpochMilli());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
