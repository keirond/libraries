package org.keiron.libraries.web.app.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.model.FooReq;
import org.keiron.libraries.web.app.model.FooRes;
import org.keiron.libraries.web.app.server.base.BaseRes;
import org.keiron.libraries.web.app.service.FooSvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping(path = "/v1/foo")
@RequiredArgsConstructor
public class FooHttpCtrl {

  private final FooSvc fooSvc;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<BaseRes<FooRes>>> getOne(@RequestParam @NotBlank String id) {
    return fooSvc
        .getById(id)
        .map(fooRes -> new BaseRes<FooRes>().setData(fooRes))
        .map(ResponseEntity::ok);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<BaseRes<FooRes>>> createOne(@RequestBody @Valid FooReq request) {
    return fooSvc
        .create(request)
        .map(fooRes -> new BaseRes<FooRes>().setData(fooRes))
        .map(ResponseEntity::ok);
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<BaseRes<FooRes>>> updateOne(@RequestParam @NotBlank String id,
      @RequestBody @Valid FooReq request) {
    return fooSvc
        .update(id, request)
        .map(fooRes -> new BaseRes<FooRes>().setData(fooRes))
        .map(ResponseEntity::ok);
  }

  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<BaseRes<Boolean>>> deleteOne(@RequestParam @NotBlank String id) {
    return fooSvc
        .delete(id)
        .map(boolRes -> new BaseRes<Boolean>().setData(boolRes))
        .map(ResponseEntity::ok);
  }

}
