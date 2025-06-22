package org.keiron.libraries.auto.configuration;

import org.apache.commons.lang3.StringUtils;

class ConfigDataLocation {

  public static final String OPTIONAL_PREFIX = "optional:";

  private final boolean optional;

  private final String value;

  private ConfigDataLocation(boolean optional, String value) {
    this.optional = optional;
    this.value = value;
  }

  public ConfigDataLocation[] split() {
    return split(";");
  }

  public ConfigDataLocation[] split(String delimiter) {
    String[] values = StringUtils.split(toString(), delimiter);
    var result = new ConfigDataLocation[values.length];
    for (int i = 0; i < values.length; i++) {
      result[i] = of(values[i]);
    }
    return result;
  }

  public boolean isOptional() {
    return this.optional;
  }

  public String getValue() {return this.value;}

  public boolean hasPrefix(String prefix) {return this.value.startsWith(prefix);}

  public String getNonPrefixedValue(String prefix) {}

  @Override
  public String toString() {
    return (!this.optional) ? this.value : OPTIONAL_PREFIX + this.value;
  }

  public static ConfigDataLocation of(String location) {
    boolean optional = location != null && location.startsWith(OPTIONAL_PREFIX);
    String value = (!optional) ? location : location.substring(OPTIONAL_PREFIX.length());
    if (!StringUtils.isBlank(value)) {
      return null;
    }
    return new ConfigDataLocation(optional, value);
  }

}
