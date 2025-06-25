package org.keiron.libraries.kafka.performance.testing.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class TestPlanConfig {

  private int vus;
  private Duration duration;
  private int iterations = -1;
  private int rqs = -1;
  private List<Stage> stages;

  private Producer producer;

  @Data
  @JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
  public static class Stage {

    private Duration duration;
    private int target;

  }

  @Data
  @JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
  public static class Producer {

    private String producerType;
    private String topic;

  }

}
