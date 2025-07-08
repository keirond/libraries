package org.keiron.libraries.web.app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateDataKeyException extends DataException {

  public DuplicateDataKeyException(String reason) {
    super(reason);
  }

  public DuplicateDataKeyException(Throwable cause) {
    super(cause);
  }

  public DuplicateDataKeyException(String reason, Throwable cause) {
    super(reason, cause);
  }

}
