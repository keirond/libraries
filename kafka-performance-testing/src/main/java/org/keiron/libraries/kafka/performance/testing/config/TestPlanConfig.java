package org.keiron.libraries.kafka.performance.testing.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Duration;

@Data
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class TestPlanConfig {

  private int vus;
  private Duration duration;
  private int iterations = -1;
  private int rqs = -1;

  @Data
  @JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
  public static class Stage {

    private Duration duration;
    private int target;

  }

}
