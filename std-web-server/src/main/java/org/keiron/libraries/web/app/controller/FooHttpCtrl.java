package org.keiron.libraries.web.app.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/foo", consumes = {MediaType.APPLICATION_JSON_VALUE})
public class FooHttpCtrl {

}
