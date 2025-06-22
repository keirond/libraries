package org.keiron.libraries.auto.configuration;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

class StandardConfigLocationResolver implements ConfigDataLocationResolver {

  @Override
  public boolean isResolvable(ConfigDataLocationResolverContext context,
      ConfigDataResource resource) {
    return true;
  }

  @Override
  public List<StandardConfigDataResource> resolve(ConfigDataLocationResolverContext context,
      ConfigDataLocation location) {
    return List.of();
  }

  private Set<StandardConfigDataReference> getReferences(ConfigDataLocationResolverContext context,
      ConfigDataLocation[] configDataLocations) {
    var references = new LinkedHashSet<StandardConfigDataReference>();
    for (ConfigDataLocation configDataLocation : configDataLocations) {
      references.addAll(getReferences(context, configDataLocation));
    }
    return references;
  }

  private Set<StandardConfigDataReference> getReferences(ConfigDataLocationResolverContext context,
      ConfigDataLocation location) {
    return null;
  }

}
