package org.keiron.libraries.cassandra.perfromance.testing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProducerConfig {

  private String bootstrapServers;

  public Map<String, Object> toMapConfig() {

    return ObjectMapper.convertValue(this, new TypeReference<>() {});
  }

}
