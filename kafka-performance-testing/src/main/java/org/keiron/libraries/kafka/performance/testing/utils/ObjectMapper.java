package org.keiron.libraries.kafka.performance.testing.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

import java.time.Duration;

public class ObjectMapper {

  @Getter
  private static final com.fasterxml.jackson.databind.ObjectMapper mapper;

  static {
    mapper = new com.fasterxml.jackson.databind.ObjectMapper(new YAMLFactory());
    mapper.registerModule(new JavaTimeModule());
    mapper.registerModule(
        new SimpleModule().addDeserializer(Duration.class, new FriendlyDurationDeserializer()));
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
  }

}
