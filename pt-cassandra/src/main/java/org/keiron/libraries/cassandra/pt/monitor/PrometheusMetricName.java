package org.keiron.libraries.cassandra.pt.monitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrometheusMetricName {

  PRODUCE_MESSAGE("produce_message");

  private final String name;

}
