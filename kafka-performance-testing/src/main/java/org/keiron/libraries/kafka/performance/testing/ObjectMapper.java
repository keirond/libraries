package org.keiron.libraries.kafka.performance.testing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;

public class ObjectMapper {

  private static final com.fasterxml.jackson.databind.ObjectMapper mapper;

  static {

    mapper = new com.fasterxml.jackson.databind.ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static <T> T convertValue(Object fromValue, Class<T> toValueType) {

    return mapper.convertValue(fromValue, toValueType);
  }

  public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {

    return mapper.convertValue(fromValue, toValueTypeRef);
  }

}
