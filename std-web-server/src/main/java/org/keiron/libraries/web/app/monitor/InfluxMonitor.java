package org.keiron.libraries.web.app.monitor;

import io.micrometer.core.instrument.Tag;
import io.micrometer.influx.InfluxMeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InfluxMonitor implements Monitor {

  private final InfluxMeterRegistry registry;

  @Override
  public Mono<Void> counter(String metricId, double amount, Tag... tags) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> gauge(String metricId, double amount, Tag... tags) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> histogram(String metricId, double amount, Tag... tags) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> timer(String metricId, double amount, Tag... tags) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> summary(String metricId, double amount, Tag... tags) {
    return Mono.empty();
  }

}
