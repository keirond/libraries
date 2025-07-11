package org.keiron.libraries.kafka.pt.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class ProducerConfig {

  private String bootstrapServers;
  private String compressionType;
  private String acks;

}
