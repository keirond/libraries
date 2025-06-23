package org.keiron.libraries.kafka.performance.testing.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Duration;

public class FriendlyDurationDeserializer extends JsonDeserializer<Duration> {

  @Override
  public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    var text = p.getText().trim().toLowerCase();
    if (text.endsWith("ms"))
      return Duration.ofMillis(Long.parseLong(text.replace("ms", "")));
    if (text.endsWith("s"))
      return Duration.ofSeconds(Long.parseLong(text.replace("s", "")));
    if (text.endsWith("m"))
      return Duration.ofMinutes(Long.parseLong(text.replace("m", "")));
    if (text.endsWith("h"))
      return Duration.ofHours(Long.parseLong(text.replace("h", "")));
    return Duration.parse(text); // fallback to ISO-8601
  }

}