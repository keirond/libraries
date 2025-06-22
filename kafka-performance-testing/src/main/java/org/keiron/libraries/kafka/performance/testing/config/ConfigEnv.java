package org.keiron.libraries.kafka.performance.testing.config;

class ConfigEnv {

  public static String getEnvConfig(String envKey, String defaultValue) {
    var path = System.getenv(envKey);
    return path != null ? path : defaultValue;
  }

}
