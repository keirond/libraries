package org.keiron.libraries.auto.configuration;

abstract class ConfigDataReference {

  private final boolean optional;

  public ConfigDataReference() {this(false);}

  protected ConfigDataReference(boolean optional) {this.optional = optional;}

  boolean isOptional() {return optional;}

}
