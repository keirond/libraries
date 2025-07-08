package org.keiron.libraries.web.app.controller;

import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.exception.DataNotFoundException;
import org.keiron.libraries.web.app.model.FooRes;
import org.keiron.libraries.web.app.server.base.BaseRes;
import org.keiron.libraries.web.app.server.base.ErrorE;
import org.keiron.libraries.web.app.server.base.ErrorInfo;
import org.keiron.libraries.web.app.service.FooSvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/v1/foo", consumes = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class FooHttpCtrl {

  private final FooSvc fooSvc;

  @GetMapping(path = "")
  public Mono<ResponseEntity<BaseRes<FooRes>>> get(@RequestParam String id) {
    return fooSvc
        .getById(id)
        .map(fooRes -> new BaseRes<FooRes>().setData(fooRes))
        .map(ResponseEntity::ok)
        .onErrorResume(DataNotFoundException.class, e -> Mono.just(ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new BaseRes<FooRes>().setError(ErrorInfo.of(ErrorE.RESOURCE_NOT_FOUND)))));
  }

}
