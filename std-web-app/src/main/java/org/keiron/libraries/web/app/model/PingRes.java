package org.keiron.libraries.web.app.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class PingRes {

  private long reqEpochMillis;
  private long resEpochMillis;

}
