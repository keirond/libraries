package org.keiron.libraries.web.app.model;

import lombok.Data;

@Data
public class PingRes {

  private long reqEpochMillis;
  private long resEpochMillis;

}
