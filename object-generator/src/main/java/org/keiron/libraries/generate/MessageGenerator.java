package org.keiron.libraries.generate;

import com.fasterxml.jackson.databind.JsonNode;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageGenerator implements FieldGenerator<Map<String, Object>> {

  private static final SecureRandom random = new SecureRandom();
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  private final JsonNode messageSchema = FieldContext.messageSchema;

  @Override
  public Map<String, Object> generate() {
    var result = new HashMap<String, Object>();
    var fieldsNode = messageSchema.get("fields");
    fieldsNode.properties().forEach(entry -> {
      var key = entry.getKey();
      var valueNode = entry.getValue();
      var type = valueNode.get("_type").asText();
      switch (type) {
        case "uuid" -> result.put(key, UUID.randomUUID().toString());
        case "random_string" -> {
          int length = valueNode.get("_length").asOptional().map(JsonNode::asInt).orElse(1);
          String regex = valueNode.get("_regex").asOptional().map(JsonNode::asText).orElse(null);
          result.put(key, generateString(length, regex));
        }
      }
    });
    return result;
  }

  private String generateString(int length, String regex) { // TODO: regex support
    if (length < 1)
      throw new IllegalArgumentException("Length must be >= 1");
    var sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int index = random.nextInt(CHARACTERS.length());
      sb.append(CHARACTERS.charAt(index));
    }
    return sb.toString();
  }

}
