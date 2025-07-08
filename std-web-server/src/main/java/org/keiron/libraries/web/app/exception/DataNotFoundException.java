package org.keiron.libraries.web.app.exception;

import lombok.NoArgsConstructor;

@AppException
@NoArgsConstructor
public class DataNotFoundException extends DataException {

  public DataNotFoundException(String reason) {
    super(reason);
  }

  public DataNotFoundException(Throwable cause) {
    super(cause);
  }

  public DataNotFoundException(String reason, Throwable cause) {
    super(reason, cause);
  }

}
