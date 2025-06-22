package org.keiron.libraries.auto.configuration;

abstract class ConfigDataResource {

  private final boolean optional;

  public ConfigDataResource() {this(false);}

  protected ConfigDataResource(boolean optional) {this.optional = optional;}

  boolean isOptional() {return optional;}

}
