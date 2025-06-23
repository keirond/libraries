package org.keiron.libraries.kafka.performance.testing.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.keiron.libraries.kafka.performance.testing.config.ConfigContext;
import org.keiron.libraries.kafka.performance.testing.config.ProducerConfig;

import java.util.HashMap;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;

public class SimpleProducer {

  private static final ProducerConfig producerConfig = ConfigContext.producerConfig;

  private <K, V> Producer<K, V> createProducer(Serializer<K> keySerializer,
      Serializer<V> valueSerializer) {

    var configMap = new HashMap<String, Object>();
    configMap.put(BOOTSTRAP_SERVERS_CONFIG, producerConfig.getBootstrapServers());

    return new KafkaProducer<>(configMap, keySerializer, valueSerializer);
  }

}
