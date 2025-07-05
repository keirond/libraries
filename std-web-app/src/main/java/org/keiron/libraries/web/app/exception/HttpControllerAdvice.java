package org.keiron.libraries.web.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpControllerAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> fallback(Exception e) {
    return ResponseEntity.internalServerError().body(e.getMessage());
  }

}
