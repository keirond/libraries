package org.keiron.libraries.web.app.monitor;

import reactor.core.publisher.Mono;

interface Monitor {

  Mono<Void> counter();

}
