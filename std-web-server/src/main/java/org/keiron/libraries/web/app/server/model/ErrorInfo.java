package org.keiron.libraries.web.app.server.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorInfo {

  /**
   * The error code.
   */
  private @Nullable String code;

  /**
   * A description of the reason for the error.
   */
  private @Nullable String message;

  /**
   * The parameter of the request that caused the error.
   */
  private @Nullable String param;

  /**
   * A reference to the error category this error belongs to.
   */
  private ErrorType type;

  /**
   * A link to the documentation for the specific error code.
   */
  private @Nullable String docUrl;

}
