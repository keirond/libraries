package org.keiron.libraries.kafka.performance.testing.generator;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.keiron.libraries.kafka.performance.testing.config.ConfigContext;
import org.keiron.libraries.kafka.performance.testing.config.ConfigEnv;
import org.keiron.libraries.kafka.performance.testing.utils.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FieldContext {

  private static final String MESSAGE_SCHEMA_PATH = ConfigEnv.getEnvConfig("MESSAGE_SCHEMA_PATH",
      "object/message.json");
  public static JsonNode messageSchema;

  static {
    messageSchema = load(MESSAGE_SCHEMA_PATH);
  }

  private static JsonNode load(String resourcePath) {
    try (InputStream is = ConfigContext.class.getClassLoader().getResourceAsStream(resourcePath)) {
      if (is == null) {
        throw new IllegalArgumentException(String.format("Resource '%s' not found", resourcePath));
      }

      var config = ObjectMapper.getMapper().readTree(is);
      log.info("Loaded schema from {}:\n{}", resourcePath,
          ObjectMapper.getMapper().writeValueAsString(config));
      return config;
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to load JSON schema from: " + resourcePath, ex);
    }
  }

}
