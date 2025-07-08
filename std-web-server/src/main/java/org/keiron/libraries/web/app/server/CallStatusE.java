package org.keiron.libraries.web.app.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CallStatusE {

  SUCCESS(1000, "Success"),


  ;

  private final int code;
  private final String message;

}
