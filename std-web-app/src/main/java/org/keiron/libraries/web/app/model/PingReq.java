package org.keiron.libraries.web.app.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PingReq {

  private long reqEpochMillis;

}
