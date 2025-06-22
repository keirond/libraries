package org.keiron.libraries.kafka.performance.testing.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ConfigContext {

  private static final String KAFKA_PRODUCER_CONFIG_PATH = "kafka-producer.yaml";
  public static KafkaProducerConfig kafkaProducerConfig;

  private static final String KAFKA_CONSUMER_CONFIG_PATH = "kafka-consumer.yaml";
  public static KafkaConsumerConfig kafkaConsumerConfig;

  static {
    kafkaProducerConfig = load(KAFKA_PRODUCER_CONFIG_PATH, KafkaProducerConfig.class);
    kafkaConsumerConfig = load(KAFKA_CONSUMER_CONFIG_PATH, KafkaConsumerConfig.class);
  }

  private static <T> T load(String resourcePath, Class<T> clazz) {
    try (InputStream is = ConfigContext.class.getClassLoader().getResourceAsStream(resourcePath)) {
      if (is == null) {
        throw new IllegalArgumentException(String.format("Resource '%s' not found", resourcePath));
      }
      var mapper = new ObjectMapper(new YAMLFactory());
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      var config = mapper.readValue(is, clazz);
      log.info("Loaded config from {}:\n{}", resourcePath, mapper.writeValueAsString(config));
      return config;
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to load YAML config from: " + resourcePath, ex);
    }
  }

}
