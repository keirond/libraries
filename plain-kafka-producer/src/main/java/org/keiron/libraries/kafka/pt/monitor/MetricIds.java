package org.keiron.libraries.kafka.pt.monitor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MetricIds {

  PRODUCE_MESSAGE("produce_message");

  private final String name;

}
