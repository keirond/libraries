package org.keiron.libraries.web.app.service;

import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.entity.FooEntity;
import org.keiron.libraries.web.app.model.BatchOpsRes;
import org.keiron.libraries.web.app.model.FooReq;
import org.keiron.libraries.web.app.model.FooRes;
import org.keiron.libraries.web.app.repository.InMemoryFooRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FooSvc {

  private final InMemoryFooRepo fooRepo;

  public Mono<FooRes> getById(String id) {
    return fooRepo.findById(id).map(this::fromFooEntityToFooRes);
  }

  public Mono<Page<FooRes>> getAll(PageRequest pageRequest) {
    return fooRepo
        .findAll(pageRequest)
        .map(entityPage -> new PageImpl<>(
            entityPage.getContent().stream().map(this::fromFooEntityToFooRes).toList(), pageRequest,
            entityPage.getTotalElements()));
  }

  public Mono<FooRes> create(FooReq command) {
    var id = UUID.randomUUID().toString();
    var entity = fromFooReqToFooEntity(id, command);
    return fooRepo.insert(entity).map(this::fromFooEntityToFooRes);
  }

  public Mono<BatchOpsRes<FooRes>> create(List<FooReq> commands) {
    var entities = commands.stream().map(command -> {
      var id = UUID.randomUUID().toString();
      return fromFooReqToFooEntity(id, command);
    }).toList();
    return fooRepo
        .batchInsert(entities)
        .map(batchRes -> convertBatchOpsRes(batchRes, this::fromFooEntityToFooRes));
  }

  public Mono<FooRes> update(String id, FooReq command) {
    var entity = fromFooReqToFooEntity(id, command);
    return fooRepo.update(entity).map(this::fromFooEntityToFooRes);
  }

  public Mono<BatchOpsRes<FooRes>> update(Map<String, FooReq> commands) {
    var entities = commands
        .entrySet()
        .stream()
        .map(entry -> fromFooReqToFooEntity(entry.getKey(), entry.getValue()))
        .toList();
    return fooRepo
        .batchInsert(entities)
        .map(batchRes -> convertBatchOpsRes(batchRes, this::fromFooEntityToFooRes));
  }

  public Mono<Boolean> delete(String id) {
    return fooRepo.delete(id);
  }

  public Mono<BatchOpsRes<String>> delete(List<String> ids) {
    return fooRepo.batchDelete(ids);
  }

  private FooRes fromFooEntityToFooRes(FooEntity fooEntity) {
    return new FooRes()
        .setId(fooEntity.getId())
        .setName(fooEntity.getName())
        .setDescription(fooEntity.getDescription())
        .setValue(fooEntity.getValue())
        .setTimestamp(fooEntity.getTimestamp());
  }

  private FooEntity fromFooReqToFooEntity(String id, FooReq fooReq) {
    return new FooEntity()
        .setId(id)
        .setName(fooReq.getName())
        .setDescription(fooReq.getDescription())
        .setValue(fooReq.getValue())
        .setTimestamp(fooReq.getTimestamp());
  }

  private <T, R> BatchOpsRes<T> convertBatchOpsRes(BatchOpsRes<R> batchRes, Function<R, T> mapper) {
    return new BatchOpsRes<T>()
        .setSuccess(batchRes.getSuccess().stream().map(mapper).toList())
        .setFailure(batchRes
            .getFailure()
            .stream()
            .map(failedItem -> new BatchOpsRes.FailedItem<>(mapper.apply(failedItem.item()),
                failedItem.reason()))
            .toList());
  }

}
