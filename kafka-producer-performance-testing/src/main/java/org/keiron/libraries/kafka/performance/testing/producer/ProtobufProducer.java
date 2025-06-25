package org.keiron.libraries.kafka.performance.testing.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Message;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.keiron.libraries.generate.ObjectGenerator;
import org.keiron.libraries.kafka.performance.testing.config.ConfigContext;
import org.keiron.libraries.kafka.performance.testing.config.SchemaRegistryConfig;

import java.util.HashMap;

@Slf4j
public class ProtobufProducer<T extends Message> implements Producer<Object> {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final ObjectGenerator objectGenerator = new ObjectGenerator();

  private static final SchemaRegistryConfig schemaRegistryConfig = ConfigContext.schemaRegistryConfig;

  private final org.apache.kafka.clients.producer.Producer<String, T> producer;

  public <T extends Message> ProtobufProducer() {
    var extendConfigs = new HashMap<String, Object>();
    extendConfigs.put("schema.registry.url", schemaRegistryConfig.getUrl());
    producer = ProducerFactory.createProducer(extendConfigs, new StringSerializer(),
        new KafkaProtobufSerializer<T>()); // TODO
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
    //    try {
    //      var message = objectGenerator.generate();
    //      send(topic, OBJECT_MAPPER.writeValueAsString(message));
    //      return true;
    //    } catch (Exception e) {
    //      log.warn("Error processing '{}'", e.getMessage());
    //      return false;
    //    }
  }

  @Override
  public void close() {
    producer.flush();
    producer.close();
  }

}
