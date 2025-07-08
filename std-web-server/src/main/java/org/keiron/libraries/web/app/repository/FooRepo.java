package org.keiron.libraries.web.app.repository;

import org.keiron.libraries.web.app.entity.FooEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

public interface FooRepo {

  Mono<FooEntity> save(FooEntity foo);

  Mono<FooEntity> insert(FooEntity foo);

  Mono<FooEntity> findById(String id);

  Mono<Page<FooEntity>> findAll(PageRequest pageRequest);

  Mono<FooEntity> update(FooEntity foo);

  Mono<Void> delete(String id);

}
