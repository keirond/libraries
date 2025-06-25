package org.keiron.libraries.generate;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.CharSet;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ObjectGenerator implements Generator<Map<String, Object>> {

  private static final SecureRandom random = new SecureRandom();
  private static final String CHARACTERS = CharSet.getInstance("a-z", "A-Z", "0-9").toString();

  private final JsonNode messageSchema = SchemaContext.messageSchema;

  @Override
  public Map<String, Object> generate() {
    var result = new HashMap<String, Object>();
    var fieldsNode = messageSchema.get("fields");
    fieldsNode.properties().forEach(entry -> {
      var key = entry.getKey();
      var node = entry.getValue();
      var type = node.path("_type").asText();
      switch (type) {
        case "uuid" -> result.put(key, UUID.randomUUID().toString());
        case "now" -> result.put(key, generateNow(node.path("_format").asText("ISO8601"),
            node.path("_timezone").asText("UTC")));
        case "random_string" -> result.put(key,
            generateString(node.path("_length").asInt(1), node.path("_regex").asText(null)));
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

  private Object generateNow(String format, String timezone) {
    ZoneId id = timezone != null ? ZoneId.of(timezone) : ZoneOffset.UTC;
    var now = Instant.now();
    return switch (format) {
      case "epoch" -> now.getEpochSecond();
      case "epoch_millis" -> now.toEpochMilli();
      case "ISO8601" -> now.toString();
      default -> {
        try {
          var formatter = DateTimeFormatter.ofPattern(format).withZone(id);
          yield formatter.format(now);
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Invalid format: " + format);
        }
      }
    };
  }

}
