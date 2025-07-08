package org.keiron.libraries.web.app.exception;

import lombok.NoArgsConstructor;

@AppException
@NoArgsConstructor
public class DataException extends Exception {

  public DataException(String reason) {
    super(reason);
  }

  public DataException(Throwable cause) {
    super(cause);
  }

  public DataException(String reason, Throwable cause) {
    super(reason, cause);
  }

}