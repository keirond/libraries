package org.keiron.libraries.kafka.pt.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Duration;

@Data
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class OptionsConfig {

  private int vus;
  private Duration duration;
  private int iterations = -1;

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

    private int noOfProducers;
    private String producerType;
    private String topic;

  }

}
