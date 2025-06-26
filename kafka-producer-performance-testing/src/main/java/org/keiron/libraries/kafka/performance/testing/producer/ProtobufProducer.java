package org.keiron.libraries.kafka.performance.testing.producer;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.keiron.libraries.kafka.performance.testing.config.ConfigContext;
import org.keiron.libraries.kafka.performance.testing.config.SchemaRegistryConfig;

import java.util.HashMap;

@Slf4j
public class ProtobufProducer implements Producer<Object> {

  private static final SchemaRegistryConfig schemaRegistryConfig = ConfigContext.schemaRegistryConfig;

  private final org.apache.kafka.clients.producer.Producer<String, Object> producer;

  public ProtobufProducer() {
    var extendConfigs = new HashMap<String, Object>();
    extendConfigs.put("schema.registry.url", schemaRegistryConfig.getUrl());
    producer = ProducerFactory.createProducer(extendConfigs, new StringSerializer(),
        new KafkaAvroSerializer()); // TODO replace it to KafkaProtobufSerializer
  }

  @Override
  public void send(String topic, Object message) {
    send(topic, null, message);
  }

  @Override
  public void send(String topic, String key, Object message) {
    var record = new ProducerRecord<>(topic, key, message);
    try {
      producer.send(record, (onCompletion, exception) -> {
        if (exception != null)
          log.warn("Error sending proto record {}", exception.getMessage());
      });
    } catch (Exception e) {
      log.warn("Exception sending proto record {}", e.getMessage());
    }
  }

  @Override
  public boolean runTest(String topic) {
    return false;
  }

  @Override
  public void close() {
    producer.flush();
    producer.close();
  }

}
