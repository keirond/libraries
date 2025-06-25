package org.keiron.libraries.kafka.performance.testing.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.keiron.libraries.generate.ObjectGenerator;

@Slf4j
public class ProtobufProducer implements Producer<Object> {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final ObjectGenerator objectGenerator = new ObjectGenerator();

  private final org.apache.kafka.clients.producer.Producer<String, Object> producer;

  public ProtobufProducer() {
    producer = ProducerFactory.createProducer(null, new StringSerializer(),
        new KafkaAvroSerializer());
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
          log.warn("Error sending avro record {}", exception.getMessage());
      });
    } catch (Exception e) {
      log.warn("Exception sending avro record {}", e.getMessage());
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

}
