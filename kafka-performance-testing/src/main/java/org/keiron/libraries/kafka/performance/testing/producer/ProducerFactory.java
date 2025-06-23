package org.keiron.libraries.kafka.performance.testing.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.Serializer;
import org.keiron.libraries.kafka.performance.testing.config.ConfigContext;
import org.keiron.libraries.kafka.performance.testing.config.ProducerConfig;

import java.util.HashMap;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

class ProducerFactory {

  private static final ProducerConfig producerConfig = ConfigContext.producerConfig;

  static <K, V> Producer<K, V> createProducer(Serializer<K> keySerializer,
      Serializer<V> valueSerializer) {

    var configMap = new HashMap<String, Object>();
    configMap.put(BOOTSTRAP_SERVERS_CONFIG, producerConfig.getBootstrapServers());
    configMap.put(COMPRESSION_TYPE_CONFIG, producerConfig.getCompressionType());
    configMap.put(ACKS_CONFIG, producerConfig.getAcks());

    return new KafkaProducer<>(configMap, keySerializer, valueSerializer);
  }

}
