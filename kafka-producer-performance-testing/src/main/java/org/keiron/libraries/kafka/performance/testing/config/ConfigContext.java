package org.keiron.libraries.kafka.performance.testing.config;

import lombok.extern.slf4j.Slf4j;
import org.keiron.libraries.generate.SchemaContext;
import org.keiron.libraries.kafka.performance.testing.utils.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ConfigContext {

  private static final String PRODUCER_CONFIG_PATH = ConfigEnv.getEnvConfig("PRODUCER_CONFIG_PATH",
      "kafka-producer.yaml");
  public static ProducerConfig producerConfig;

  private static final String CONSUMER_CONFIG_PATH = ConfigEnv.getEnvConfig("PRODUCER_CONFIG_PATH",
      "kafka-consumer.yaml");
  public static ConsumerConfig consumerConfig;

  private static final String TEST_PLAN_CONFIG_PATH = ConfigEnv.getEnvConfig(
      "TEST_PLAN_CONFIG_PATH", "plans/test-plan.yaml");
  public static TestPlanConfig testPlanConfig;

  private static final String MONITOR_CONFIG_PATH = ConfigEnv.getEnvConfig("MONITOR_CONFIG_PATH",
      "monitor.yaml");
  public static MonitorConfig monitorConfig;

  private static final String MESSAGE_SCHEMA_PATH = ConfigEnv.getEnvConfig("MONITOR_CONFIG_PATH",
      "object/message.json");

  static {
    producerConfig = load(PRODUCER_CONFIG_PATH, ProducerConfig.class);
    consumerConfig = load(CONSUMER_CONFIG_PATH, ConsumerConfig.class);
    testPlanConfig = load(TEST_PLAN_CONFIG_PATH, TestPlanConfig.class);
    monitorConfig = load(MONITOR_CONFIG_PATH, MonitorConfig.class);

    SchemaContext.load(MESSAGE_SCHEMA_PATH);
  }

  private static <T> T load(String resourcePath, Class<T> clazz) {
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
