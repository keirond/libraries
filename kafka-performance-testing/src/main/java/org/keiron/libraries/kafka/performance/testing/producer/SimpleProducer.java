package org.keiron.libraries.kafka.performance.testing.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

public class SimpleProducer {

  private <K, V> Producer<K, V> createProducer() {
    var properties = new Properties();

    return new KafkaProducer<>(properties);
  }

}
