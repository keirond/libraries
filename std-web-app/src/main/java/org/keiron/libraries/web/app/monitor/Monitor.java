package org.keiron.libraries.web.app.monitor;

import io.micrometer.core.instrument.Tags;
import reactor.core.publisher.Mono;

interface Monitor {

  Mono<Void> counter(String metricId, Tags tags);

}
