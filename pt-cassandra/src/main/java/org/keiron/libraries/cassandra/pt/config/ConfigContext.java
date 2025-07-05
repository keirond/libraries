package org.keiron.libraries.cassandra.pt.config;

import lombok.extern.slf4j.Slf4j;
import org.keiron.libraries.cassandra.pt.utils.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ConfigContext {

  private static final String MONITOR_CONFIG_PATH = ConfigEnv.getEnvConfig("MONITOR_CONFIG_PATH",
      "monitor.yaml");
  public static MonitorConfig monitorConfig;

  static {
    monitorConfig = load(MONITOR_CONFIG_PATH, MonitorConfig.class);
  }

  public static <T> T load(String resourcePath, Class<T> clazz) {
    try (InputStream is = ConfigContext.class.getClassLoader().getResourceAsStream(resourcePath)) {
      if (is == null) {
        throw new IllegalArgumentException(String.format("Resource '%s' not found", resourcePath));
      }

      var config = ObjectMapper.getMapper().readValue(is, clazz);
      log.info("Loaded config from {}:\n{}", resourcePath,
          ObjectMapper.getMapper().writeValueAsString(config));
      return config;
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to load YAML config from: " + resourcePath, ex);
    }
  }

}
