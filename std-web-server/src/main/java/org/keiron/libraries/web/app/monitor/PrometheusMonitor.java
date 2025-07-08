package org.keiron.libraries.web.app.monitor;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.influx.InfluxMeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PrometheusMonitor implements Monitor {

  private final PrometheusMeterRegistry registry;

  @Override
  public Mono<Void> counter(String metricId, double amount, Tag... tags) {
    return Mono.fromRunnable(() -> registry.counter(metricId, Tags.of(tags)).increment(amount));
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
