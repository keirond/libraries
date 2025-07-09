package org.keiron.libraries.web.app.server.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.keiron.libraries.web.app.server.base.ErrorType.API_ERROR;
import static org.keiron.libraries.web.app.server.base.ErrorType.INVALID_REQUEST_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorE {

  UNSPECIFIED("unspecified", "Error isn't specified for details", API_ERROR),
  INVALID_REQUEST("invalid_request", "Invalid request", INVALID_REQUEST_ERROR),
  RESOURCE_NOT_FOUND("resource_not_found", "Resource doesn't exist", API_ERROR),
  DUPLICATE_DATA_KEY("duplicate_data_key", "Data is duplicated", API_ERROR),
  ;

  private final String code;
  private final String message;
  private final ErrorType type;

}
