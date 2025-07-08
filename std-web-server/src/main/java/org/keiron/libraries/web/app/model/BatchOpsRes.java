package org.keiron.libraries.web.app.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BatchOpsRes<T> {

  private List<T> success;
  private List<FailedItem<T>> failure;

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record FailedItem<T>(T item, String message) {}

}
