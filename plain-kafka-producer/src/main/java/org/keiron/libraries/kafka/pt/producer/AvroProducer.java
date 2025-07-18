package org.keiron.libraries.kafka.pt.producer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.keiron.libraries.kafka.pt.config.ConfigContext;
import org.keiron.libraries.kafka.pt.config.SchemaRegistryConfig;
import org.keiron.libraries.kafka.pt.generate.ObjectGenerator;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AvroProducer implements Producer<Object> {

  private static final String AVRO_MESSAGE_SCHEMA_PATH = "object/message.avsc";

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final ObjectGenerator objectGenerator = new ObjectGenerator();

  private static final SchemaRegistryConfig schemaRegistryConfig = ConfigContext.schemaRegistryConfig;
  private final Schema schema;

  private final org.apache.kafka.clients.producer.Producer<String, Object> producer;

  public AvroProducer() {
    var extendConfigs = new HashMap<String, Object>();
    extendConfigs.put("schema.registry.url", schemaRegistryConfig.getUrl());
    extendConfigs.put("value.subject.name.strategy",
        "io.confluent.kafka.serializers.subject.TopicRecordNameStrategy");
    var valueSerializers = new KafkaAvroSerializer();
    valueSerializers.configure(extendConfigs, false);

    var parser = new Schema.Parser();
    var node = ConfigContext.load(AVRO_MESSAGE_SCHEMA_PATH, JsonNode.class);

    var nodeStr = "";
    try {
      nodeStr = OBJECT_MAPPER.writeValueAsString(node);
    } catch (Exception ignored) {
    }
    schema = parser.parse(nodeStr);
    producer = ProducerFactory.createProducer(Map.of(), new StringSerializer(), valueSerializers);
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
    try {
      var avroRecord = new GenericData.Record(schema);
      var data = objectGenerator.generate();
      for (var field : schema.getFields()) {
        avroRecord.put(field.name(), data.get(field.name()));
      }
      send(topic, avroRecord);
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
