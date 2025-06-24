package org.keiron.libraries.kafka.performance.testing.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

@Slf4j
public class StringProducer implements Producer<String> {

  private final org.apache.kafka.clients.producer.Producer<String, String> producer;

  public StringProducer() {
    producer = ProducerFactory.createProducer(new StringSerializer(), new StringSerializer());
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
        if (exception != null) {
          log.warn("Error sending record {}", exception.getMessage());
        }
//        else {
//          log.info("Record sent to topic {}, partition {}, offset {}, at {}", onCompletion.topic(),
//              onCompletion.partition(), onCompletion.offset(), onCompletion.timestamp());
//        }
      });

    } catch (Exception e) {
      log.warn("Exception sending record {}", e.getMessage());
    }
  }

}
