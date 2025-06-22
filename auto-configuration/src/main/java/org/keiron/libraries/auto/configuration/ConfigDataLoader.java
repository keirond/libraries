package org.keiron.libraries.auto.configuration;

interface ConfigDataLoader<R extends ConfigDataResource> {

  default boolean isLoadable(ConfigDataLoaderContext context, R resource) {return true;}

  ConfigData load(ConfigDataLoaderContext context, R resource);

}
