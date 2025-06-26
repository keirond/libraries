package org.keiron.libraries.cassandra.performance.testing.monitor;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import lombok.Getter;
import org.keiron.libraries.kafka.performance.testing.monitor.PrometheusConfig;

import java.time.Duration;
import java.time.Instant;

public class PrometheusMonitor {

  @Getter
  private static final PrometheusMeterRegistry registry;

  static {
    registry = new PrometheusMeterRegistry(new PrometheusConfig());
  }

  public static void counter(String name, Tag... tags) {
    counter(name, 1, tags);
  }

  public static void counter(String name, double amount, Tag... tags) {
    registry.counter(name, Tags.of(tags)).increment(amount);
  }

  public static void gauge(String name, Tag... tags) {}

  public static void timer(String name, Instant start, Tag... tags) {
    registry.timer(name, Tags.of(tags)).record(Duration.between(start, Instant.now()).abs());
  }

  public static void meter(String name, Tag... tags) {}

}
