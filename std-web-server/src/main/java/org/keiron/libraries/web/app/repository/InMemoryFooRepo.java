package org.keiron.libraries.web.app.repository;

import org.keiron.libraries.web.app.common.Utils;
import org.keiron.libraries.web.app.entity.FooEntity;
import org.keiron.libraries.web.app.exception.DataNotFoundException;
import org.keiron.libraries.web.app.exception.DuplicateDataKeyException;
import org.keiron.libraries.web.app.model.BatchOpsRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;

@Repository
public class InMemoryFooRepo implements FooRepo {

  private final Map<String, FooEntity> storage = new ConcurrentHashMap<>();

  @Override
  public Mono<FooEntity> save(FooEntity foo) {
    return Mono.fromCallable(() -> storage.put(foo.getId(), foo));
  }

  @Override
  public Mono<FooEntity> insert(FooEntity foo) {
    return Mono.fromCallable(() -> {
      if (storage.containsKey(foo.getId())) {
        throw new DuplicateDataKeyException("Duplicate ID: %s".formatted(foo.getId()));
      }
      return storage.put(foo.getId(), foo);
    });
  }

  public Mono<BatchOpsRes<FooEntity>> batchInsert(List<FooEntity> fooEntityList) {
    return Flux
        .fromIterable(fooEntityList)
        .flatMap(foo -> insert(foo)
            .map(successFoo -> Tuples.of(Optional.of(successFoo),
                Optional.<BatchOpsRes.FailedItem<FooEntity>>empty()))
            .onErrorResume(e -> Mono.just(Tuples.of(Optional.empty(),
                Optional.of(new BatchOpsRes.FailedItem<>(foo, e.getMessage()))))))
        .collect(batchFooEntityCollector());
  }

  @Override
  public Mono<FooEntity> findById(String id) {
    return Mono.fromCallable(() -> {
      if (!storage.containsKey(id)) {
        throw new DataNotFoundException("Not found data with ID: %s".formatted(id));
      }
      return storage.get(id);
    });
  }

  @Override
  public Mono<Page<FooEntity>> findAll(PageRequest pageRequest) {
    var offset = pageRequest.getOffset();
    var size = pageRequest.getPageSize();
    var sort = pageRequest.getSort();
    var comparator = Utils.fromSortToComparator(sort, FooEntity.class);
    return Mono.fromCallable(() -> new PageImpl<>(
        storage.values().stream().sorted(comparator).skip(offset).limit(size).toList(), pageRequest,
        size));
  }

  @Override
  public Mono<FooEntity> update(FooEntity foo) {
    var id = foo.getId();
    return Mono.fromCallable(() -> {
      if (!storage.containsKey(id)) {
        throw new DataNotFoundException("Not found data with ID: %s".formatted(id));
      }
      return storage.put(id, foo);
    });
  }

  @Override
  public Mono<Void> delete(String id) {
    return Mono.fromRunnable(() -> storage.remove(id));
  }

  private Collector<Tuple2<Optional<FooEntity>, Optional<BatchOpsRes.FailedItem<FooEntity>>>, BatchOpsRes<FooEntity>, BatchOpsRes<FooEntity>> batchFooEntityCollector() {
    return Collector.of(BatchOpsRes::new, (result, tuple) -> {
      if (tuple.getT1().isPresent()) {
        result.getSuccess().add(tuple.getT1().get());
      } else if (tuple.getT2().isPresent()) {
        result.getFailure().add(tuple.getT2().get());
      }
    }, (u, v) -> {
      var result = new BatchOpsRes<FooEntity>();
      result.getSuccess().addAll(u.getSuccess());
      result.getSuccess().addAll(v.getSuccess());
      result.getFailure().addAll(u.getFailure());
      result.getFailure().addAll(v.getFailure());
      return result;
    });
  }

}
