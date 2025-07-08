package org.keiron.libraries.web.app.server.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.keiron.libraries.web.app.server.base.ErrorType.API_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorE {

  SUCCESS("success", "Success", API_ERROR),
  RESOURCE_NOT_FOUND("resource_not_found", "Resource doesn't exist", API_ERROR),
  ;

  private final String code;
  private final String message;
  private final ErrorType type;

}
