package vn.baodh.practices.syntax.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1/")
public class HttpController {

    @GetMapping(path = "/test")
    public Mono<ResponseEntity<?>> test(@RequestBody String id) {
        Mono.just()
        return Mono.just(ResponseEntity.ok().body("Hello " + id));
    }
}
