package org.keiron.libraries.generate;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class SchemaContext {

  public static JsonNode messageSchema;

  public static void load(String resourcePath) {
    try (InputStream is = SchemaContext.class.getClassLoader().getResourceAsStream(resourcePath)) {
      if (is == null) {
        throw new IllegalArgumentException(String.format("Resource '%s' not found", resourcePath));
      }
      var config = ObjectMapper.getMapper().readTree(is);
      log.info("Loaded schema from {}:\n{}", resourcePath,
          ObjectMapper.getMapper().writeValueAsString(config));
      messageSchema = config;
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to load JSON schema from: " + resourcePath, ex);
    }
  }

}
