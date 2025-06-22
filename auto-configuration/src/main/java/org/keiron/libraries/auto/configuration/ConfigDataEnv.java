package org.keiron.libraries.auto.configuration;

import java.util.ArrayList;

class ConfigDataEnv {

  static final String LOCATION_PROPERTY = "config.location";

  static final String ON_NOT_FOUND_PROPERTY = "config.location.on-not-found";

  static final ConfigDataLocation[] DEFAULT_SEARCH_LOCATIONS;

  static {
    var locations = new ArrayList<ConfigDataLocation>();
    locations.add(ConfigDataLocation.of(""));
    DEFAULT_SEARCH_LOCATIONS = locations.toArray(new ConfigDataLocation[0]);
  }

  private static final ConfigDataLocation[] EMPTY_LOCATIONS = new ConfigDataLocation[0];

}