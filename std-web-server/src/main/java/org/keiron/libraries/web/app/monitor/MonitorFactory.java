package org.keiron.libraries.web.app.monitor;

import io.micrometer.influx.InfluxMeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MonitorFactory {

  @Bean
  @Profile("prometheus")
  @ConditionalOnBooleanProperty(value = "management.prometheus.metrics.export.enabled")
  public Monitor prometheusMonitor(PrometheusMeterRegistry registry) {
    return new PrometheusMonitor(registry);
  }

  @Bean
  @Profile("influx")
  @ConditionalOnBooleanProperty(value = "management.influx.metrics.export.enabled")
  public Monitor influxMonitor(InfluxMeterRegistry registry) {
    return new InfluxMonitor(registry);
  }

}
