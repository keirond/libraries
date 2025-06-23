package org.keiron.libraries.kafka.performance.testing.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class ProducerConfig {

  private String bootstrapServers;

  private Long bufferMemory;
  private Long maxBlockMs;

}
