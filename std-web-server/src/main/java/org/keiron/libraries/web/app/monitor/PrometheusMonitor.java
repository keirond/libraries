package org.keiron.libraries.web.app.monitor;

import io.micrometer.common.lang.Nullable;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

/**
 * Prometheus-based implementation of {@link Monitor} that records metrics using a
 * {@link PrometheusMeterRegistry} and wraps them in Reactor {@link Mono}.
 */
@RequiredArgsConstructor
public class PrometheusMonitor implements Monitor {

  private final PrometheusMeterRegistry registry;

  /**
   * {@inheritDoc}
   */
  @Override
  public Mono<Void> counter(String metricId, double amount, Tag... tags) {
    return Mono.fromRunnable(() -> registry.counter(metricId, Tags.of(tags)).increment(amount));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends Number> Mono<Void> gauge(String metricId, T number, Tag... tags) {
    return Mono.fromRunnable(() -> registry.gauge(metricId, Tags.of(tags), number));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> Mono<Void> gauge(String metricId, @Nullable T stateObject,
      ToDoubleFunction<T> valueFunction, Tag... tags) {
    return Mono.fromRunnable(
        () -> registry.gauge(metricId, Tags.of(tags), stateObject, valueFunction));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Mono<Void> timer(String metricId, Duration duration, Tag... tags) {
    return Mono.fromRunnable(() -> registry.timer(metricId, Tags.of(tags)).record(duration));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> Mono<Void> timer(String metricId, Supplier<T> supplier, Tag... tags) {
    return Mono.fromRunnable(() -> registry.timer(metricId, Tags.of(tags)).record(supplier));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Mono<Void> summary(String metricId, double amount, Tag... tags) {
    return Mono.fromRunnable(() -> registry.summary(metricId, Tags.of(tags)).record(amount));
  }

}
