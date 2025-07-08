package org.keiron.libraries.web.app.monitor;

import io.micrometer.core.instrument.Tag;
import reactor.core.publisher.Mono;

interface Monitor {

  Mono<Void> counter(String metricId, double amount, Tag... tags);

  Mono<Void> gauge(String metricId, double amount, Tag... tags);

  Mono<Void> histogram(String metricId, double amount, Tag... tags);

  Mono<Void> timer(String metricId, double amount, Tag... tags);

  Mono<Void> summary(String metricId, double amount, Tag... tags);

}
