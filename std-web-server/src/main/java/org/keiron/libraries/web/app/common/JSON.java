package org.keiron.libraries.web.app.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.StringUtils;

public class JSON {

  private static final ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
  }

  public static ObjectMapper instance() {
    return mapper;
  }

  public static String toString(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static String toTruncatedString(Object obj) {
    int maxLength = 100;
    try {
      return StringUtils.truncate(mapper.writeValueAsString(obj), maxLength);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    try {
      return mapper.convertValue(json, clazz);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(String json, TypeReference<T> typeRef) {
    try {
      return mapper.convertValue(json, typeRef);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromObject(Object obj, Class<T> clazz) {
    try {
      return mapper.convertValue(obj, clazz);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromObject(Object obj, TypeReference<T> typeRef) {
    try {
      return mapper.convertValue(obj, typeRef);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
  }

}
