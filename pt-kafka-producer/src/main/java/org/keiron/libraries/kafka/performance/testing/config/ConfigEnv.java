package org.keiron.libraries.kafka.performance.testing.config;

public class ConfigEnv {

  public static int getEnvConfig(String envKey, int defaultValue) {
    var envValue = System.getenv(envKey);
    return envValue != null ? Integer.parseInt(envValue) : defaultValue;
  }

}
