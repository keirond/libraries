package org.keiron.libraries.cassandra.performance.testing.monitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrometheusMetricName {

  PRODUCE_MESSAGE("produce_message");

  private final String name;

}
