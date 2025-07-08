package org.keiron.libraries.web.app.monitor;

import io.micrometer.common.lang.Nullable;
import io.micrometer.core.instrument.Tag;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

/**
 * Provides a unified reactive API for recording various types of monitoring metrics.
 */
public interface Monitor {

  /**
   * Increments a counter metric by the given amount.
   *
   * @param metricId the unique identifier for the counter metric
   * @param amount   the value to increment the counter by
   * @param tags     optional tags to associate with the metric
   *
   * @return a {@link Mono} that completes when the counter is updated
   */
  Mono<Void> counter(String metricId, double amount, Tag... tags);

  /**
   * Records the current value for a gauge metric.
   *
   * @param metricId the unique identifier for the gauge metric
   * @param number   the numeric value to record
   * @param tags     optional tags to associate with the metric
   * @param <T>      a type extending {@link Number}
   *
   * @return a {@link Mono} that completes when the gauge is updated
   */
  <T extends Number> Mono<Void> gauge(String metricId, T number, Tag... tags);

  /**
   * Records the current value for a gauge metric derived from a state object.
   *
   * @param metricId      the unique identifier for the gauge metric
   * @param stateObject   the state object to extract the value from (nullable)
   * @param valueFunction a function to extract the numeric value from the state object
   * @param tags          optional tags to associate with the metric
   * @param <T>           the type of the state object
   *
   * @return a {@link Mono} that completes when the gauge is updated
   */
  <T> Mono<Void> gauge(String metricId, @Nullable T stateObject, ToDoubleFunction<T> valueFunction,
      Tag... tags);

  /**
   * Records the duration of an operation as a timer metric.
   *
   * @param metricId the unique identifier for the timer metric
   * @param duration the duration to record
   * @param tags     optional tags to associate with the metric
   *
   * @return a {@link Mono} that completes when the timer is updated
   */
  Mono<Void> timer(String metricId, Duration duration, Tag... tags);

  /**
   * Measures the execution time of a supplier and records it as a timer metric.
   *
   * @param metricId the unique identifier for the timer metric
   * @param supplier the supplier whose execution duration will be measured
   * @param tags     optional tags to associate with the metric
   * @param <T>      the type returned by the supplier
   *
   * @return a {@link Mono} that completes when the timer is updated
   */
  <T> Mono<Void> timer(String metricId, Supplier<T> supplier, Tag... tags);

  /**
   * Records a sample value for a summary metric.
   *
   * @param metricId the unique identifier for the summary metric
   * @param amount   the value to record in the summary
   * @param tags     optional tags to associate with the metric
   *
   * @return a {@link Mono} that completes when the sample has been recorded
   */
  Mono<Void> summary(String metricId, double amount, Tag... tags);

}
