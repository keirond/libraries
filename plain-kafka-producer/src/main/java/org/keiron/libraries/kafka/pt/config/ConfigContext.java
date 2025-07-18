package org.keiron.libraries.kafka.pt.config;

import lombok.extern.slf4j.Slf4j;
import org.keiron.libraries.kafka.pt.generate.SchemaContext;
import org.keiron.libraries.kafka.pt.utils.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ConfigContext {

  private static final String PRODUCER_CONFIG_PATH = "kafka-producer.yaml";
  public static ProducerConfig producerConfig;

  private static final String KAFKA_SCHEMA_REGISTRY_CONFIG_PATH = "kafka-schema-registry.yaml";
  public static SchemaRegistryConfig schemaRegistryConfig;

  private static final String OPTIONS_CONFIG_PATH = "options.yaml";
  public static OptionsConfig optionsConfig;

  private static final String MONITOR_CONFIG_PATH = "monitor.yaml";
  public static MonitorConfig monitorConfig;

  private static final String MESSAGE_SCHEMA_PATH = "object/message.json";

  static {
    producerConfig = load(PRODUCER_CONFIG_PATH, ProducerConfig.class);
    schemaRegistryConfig = load(KAFKA_SCHEMA_REGISTRY_CONFIG_PATH, SchemaRegistryConfig.class);
    optionsConfig = load(OPTIONS_CONFIG_PATH, OptionsConfig.class);
    monitorConfig = load(MONITOR_CONFIG_PATH, MonitorConfig.class);

    SchemaContext.load(MESSAGE_SCHEMA_PATH);
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
