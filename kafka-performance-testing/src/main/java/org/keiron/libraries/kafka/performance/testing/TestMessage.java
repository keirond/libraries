package org.keiron.libraries.kafka.performance.testing;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
class TestMessage {

  private String id;

}
