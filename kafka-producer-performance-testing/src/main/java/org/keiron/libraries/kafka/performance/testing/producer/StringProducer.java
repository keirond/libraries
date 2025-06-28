package org.keiron.libraries.kafka.performance.testing.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.keiron.libraries.kafka.performance.testing.generate.ObjectGenerator;

import java.util.Map;

@Slf4j
public class StringProducer implements Producer<String> {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final ObjectGenerator objectGenerator = new ObjectGenerator();

  private final org.apache.kafka.clients.producer.Producer<String, String> producer;

  public StringProducer() {
    producer = ProducerFactory.createProducer(Map.of(), new StringSerializer(),
        new StringSerializer());
  }

  @Override
  public void send(String topic, String message) {
    send(topic, null, message);
  }

  @Override
  public void send(String topic, String key, String message) {
    var record = new ProducerRecord<>(topic, key, message);
    try {
      producer.send(record, (onCompletion, exception) -> {
        if (exception != null)
          log.warn("Error sending record {}", exception.getMessage());
      });
    } catch (Exception e) {
      log.warn("Exception sending record {}", e.getMessage());
    }
  }

  @Override
  public boolean runTest(String topic) {
    try {
      var message = objectGenerator.generate();
      send(topic, OBJECT_MAPPER.writeValueAsString(message));
      return true;
    } catch (Exception e) {
      log.warn("Error processing '{}'", e.getMessage());
      return false;
    }
  }

  @Override
  public void close() {
    producer.flush();
    producer.close();
  }

}
