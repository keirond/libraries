package org.keiron.libraries.auto.configuration;

import java.util.List;

interface ConfigDataLocationResolver<R extends ConfigDataResource> {

  boolean isResolvable(ConfigDataLocationResolverContext context, R resource);

  List<R> resolve(ConfigDataLocationResolverContext context, ConfigDataLocation location);

}
