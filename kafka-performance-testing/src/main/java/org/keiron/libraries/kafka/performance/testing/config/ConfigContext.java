package org.keiron.libraries.kafka.performance.testing.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ConfigContext {

  private static final String KAFKA_PRODUCER_CONFIG_PATH = "config/kafka-producer.yaml";
  public static KafkaProducerConfig kafkaProducerConfig;

  private static final String KAFKA_CONSUMER_CONFIG_PATH = "config/kafka-consumer.yaml";
  public static KafkaConsumerConfig kafkaConsumerConfig;

  static {
    kafkaProducerConfig = load(KAFKA_PRODUCER_CONFIG_PATH, KafkaProducerConfig.class);
    kafkaConsumerConfig = load(KAFKA_CONSUMER_CONFIG_PATH, KafkaConsumerConfig.class);
  }

  private static <T> T load(String resourcePath, Class<T> clazz) {
    var file = new File(resourcePath);
    if (!file.exists()) {
      throw new IllegalArgumentException("Configuration file not found: " + resourcePath);
    }
    var mapper = new ObjectMapper(new YAMLFactory());
    try {
      var config = mapper.readValue(file, clazz);
      log.info("Loaded config from {}:\n {}", resourcePath, mapper.writeValueAsString(config));
      return config;
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to load YAML config from: " + resourcePath, ex);
    }
  }

}
