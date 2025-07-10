package org.keiron.libraries.web.app.server.http;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.keiron.libraries.web.app.exception.DataNotFoundException;
import org.keiron.libraries.web.app.exception.DuplicateDataKeyException;
import org.keiron.libraries.web.app.server.base.BaseRes;
import org.keiron.libraries.web.app.server.base.ErrorE;
import org.keiron.libraries.web.app.server.base.ErrorInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.util.ArrayList;
import java.util.HashSet;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class HttpCallExHandler {

  /**
   * <code>500 INTERNAL_SERVER_ERROR</code>
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> fallback(Exception e) {
    log.error(e.getMessage(), e);
    return ResponseEntity
        .internalServerError()
        .body(
            new BaseRes<>().setError(ErrorInfo.of(ErrorE.UNSPECIFIED).setMessage(e.getMessage())));
  }

  // 415 UNSUPPORTED_MEDIA_TYPE

  /**
   * Happened when requested media type is application/json but given text/plain.
   */
  @ExceptionHandler(UnsupportedMediaTypeStatusException.class)
  public ResponseEntity<?> handle(UnsupportedMediaTypeStatusException e) {
    return ResponseEntity
        .status(UNSUPPORTED_MEDIA_TYPE)
        .body(new BaseRes<>().setError(
            ErrorInfo.of(ErrorE.INVALID_REQUEST).setMessage(e.getMessage())));
  }

  // 405 METHOD_NOT_ALLOWED

  @ExceptionHandler(MethodNotAllowedException.class)
  public ResponseEntity<?> handle(MethodNotAllowedException e) {
    return ResponseEntity
        .status(METHOD_NOT_ALLOWED)
        .body(new BaseRes<>().setError(
            ErrorInfo.of(ErrorE.INVALID_REQUEST).setMessage(e.getMessage())));
  }

  // 404 NOT_FOUND

  /**
   * Happened when data isn't exists in resources.
   */
  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<?> handle(DataNotFoundException e) {
    return ResponseEntity
        .status(NOT_FOUND)
        .body(new BaseRes<>().setError(
            ErrorInfo.of(ErrorE.RESOURCE_NOT_FOUND).setMessage(e.getMessage())));
  }

  // 400 BAD_REQUEST

  /**
   * Happened when a requested field is not given.
   */
  @ExceptionHandler(MissingRequestValueException.class)
  public ResponseEntity<?> handle(MissingRequestValueException e) {
    return ResponseEntity
        .badRequest()
        .body(new BaseRes<>().setError(
            ErrorInfo.of(ErrorE.INVALID_REQUEST).setMessage(e.getMessage())));
  }

  /**
   * Happened when requested field type is long but given string
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handle(IllegalArgumentException e) {
    return ResponseEntity
        .badRequest()
        .body(new BaseRes<>().setError(
            ErrorInfo.of(ErrorE.INVALID_REQUEST).setMessage(e.getMessage())));
  }

  /**
   * Happened when violating constraint validations on query params, path variables, headers,
   * method-level constraints.
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handle(ConstraintViolationException e) {
    var params = new ArrayList<>();
    e.getConstraintViolations().forEach(violation -> {
      params.add(violation.getPropertyPath().toString());
    });
    return ResponseEntity
        .badRequest()
        .body(new BaseRes<>().setError(ErrorInfo
            .of(ErrorE.INVALID_REQUEST)
            .setParams(StringUtils.collectionToDelimitedString(params, ", "))
            .setMessage(e.getMessage())));
  }

  /**
   * Happened when violating constraint validations on request bodies.
   */
  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<?> handleBindException(WebExchangeBindException e) {
    var errors = e.getFieldErrors();
    var params = new HashSet<String>();
    var messages = new ArrayList<String>();
    errors.forEach(error -> {
      params.add(error.getField());
      messages.add("%s: %s".formatted(error.getField(), error.getDefaultMessage()));
    });
    return ResponseEntity
        .badRequest()
        .body(new BaseRes<>().setError(ErrorInfo
            .of(ErrorE.INVALID_REQUEST)
            .setParams(StringUtils.collectionToDelimitedString(params, ", "))
            .setMessage(StringUtils.collectionToDelimitedString(messages, "; "))));
  }

  /**
   * Happened when data is exists in resources.
   */
  @ExceptionHandler(DuplicateDataKeyException.class)
  public ResponseEntity<?> handle(DuplicateDataKeyException e) {
    return ResponseEntity
        .badRequest()
        .body(new BaseRes<>().setError(
            ErrorInfo.of(ErrorE.DUPLICATE_DATA_KEY).setMessage(e.getMessage())));
  }

}
