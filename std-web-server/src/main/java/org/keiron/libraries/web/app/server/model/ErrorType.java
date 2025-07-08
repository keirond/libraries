package org.keiron.libraries.web.app.server.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

  INVALID_REQUEST_ERROR("invalid_request_error"),
  IDEMPOTENCY_ERROR("idempotency_error"),
  API_ERROR("api_error");

  private final String name;
}
