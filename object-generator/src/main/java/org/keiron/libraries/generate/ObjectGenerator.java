package org.keiron.libraries.generate;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ObjectGenerator implements Generator<Map<String, Object>> {

  private static final SecureRandom random = new SecureRandom();
  private static final AtomicLong counter = new AtomicLong();
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  private final JsonNode messageSchema = SchemaContext.messageSchema;

  @Override
  public Map<String, Object> generate() {
    var result = new HashMap<String, Object>();
    var fieldsNode = messageSchema.get("fields");
    fieldsNode.properties().forEach(entry -> {
      var key = entry.getKey();
      var node = entry.getValue();
      var type = node.path("_type").asText("");
      var value = generateField(type, node);
      result.put(key, value);
    });
    return result;
  }

  private Object generateField(String type, JsonNode node) {
    return switch (type) {
      case "uuid" -> UUID.randomUUID().toString();
      case "now" ->
          getNow(node.path("_format").asText("ISO8601"), node.path("_timezone").asText("UTC"));
      case "constant" -> getSimpleObject(node.path("_value"));
      case "incremental" -> node.path("_start").asLong(0) + counter.getAndIncrement();
      case "random_string" ->
          generateString(node.path("_length").asInt(1), node.path("_regex").asText(null));
      case "random_int" -> {
        long min = node.path("_min").asLong(0);
        long max = node.path("_max").asLong(0);
        yield min + random.nextLong(max - min);
      }
      case "random_float" -> {
        double min = node.path("_min").asDouble(0.0);
        double max = node.path("_max").asDouble(0.0);
        int precision = node.path("_precision").asInt(0);
        yield BigDecimal.valueOf(min + random.nextDouble(max - min))
                  .setScale(precision, RoundingMode.HALF_UP).doubleValue();
      }
      case "random_pick" -> {
        var list = new ArrayList<>();
        var curr = node.path("_values");
        if (curr.isArray()) {
          for (JsonNode v : curr)
            list.add(getSimpleObject(v));
        }
        yield list.get(random.nextInt(list.size()));
      }
      //        case "custom_expression" -> null;
      default -> null;
    };
  }

  private String generateString(int length, String regex) {
    if (length < 1)
      throw new IllegalArgumentException("Length must be >= 1");

    var sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int index = random.nextInt(CHARACTERS.length());
      sb.append(CHARACTERS.charAt(index));
    }
    return sb.toString();
  }

  private Object getNow(String format, String timezone) {
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

  private Object getSimpleObject(JsonNode simpleNode) {
    return switch (simpleNode.getNodeType()) {
      case STRING -> simpleNode.asText("");
      case NUMBER -> simpleNode.asDouble(0.0);
      case BOOLEAN -> simpleNode.asBoolean(false);
      default -> null;
    };
  }

}
