package org.keiron.libraries.web.app.server.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseRes<T> {

  private @Nullable T data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable ErrorInfo error;

}
