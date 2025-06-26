package org.keiron.libraries.cassandra.performance.testing.monitor;

import io.micrometer.common.lang.Nullable;
import io.micrometer.common.util.StringUtils;
import org.keiron.libraries.kafka.performance.testing.config.ConfigContext;
import org.keiron.libraries.kafka.performance.testing.config.MonitorConfig;

class PrometheusConfig implements io.micrometer.prometheusmetrics.PrometheusConfig {

  private static final MonitorConfig monitorConfig = ConfigContext.monitorConfig;

  @Override
  public String get(@Nullable String var1) {
    if (StringUtils.isBlank(var1))
      return null;
    if ("step".equals(var1))
      return monitorConfig.getStep();
    return null;
  }

}
