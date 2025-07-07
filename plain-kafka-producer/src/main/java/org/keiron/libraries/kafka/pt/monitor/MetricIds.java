package org.keiron.libraries.kafka.pt.monitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetricIds {

  PRODUCE_MESSAGE("produce_message");

  private final String name;

}
