package org.keiron.libraries.web.app.controller;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.server.grpc.GrpcController;
import org.keiron.libraries.web.app.service.StdPingSvc;
import org.keiron.proto.ping.v1.PingOuterClass;
import org.keiron.proto.ping.v1.PingSvcGrpc;

@GrpcController
@RequiredArgsConstructor
public class PingGrpcCtrl extends PingSvcGrpc.PingSvcImplBase {

  private final StdPingSvc pingSvc;

  @Override
  public void ping(PingOuterClass.Ping request,
      StreamObserver<PingOuterClass.Pong> responseObserver) {
    PingReq command = new PingReq().setEpochMillis(request.getEpochMillis());
    pingSvc.ping(command).map(
        result -> PingOuterClass.Pong.newBuilder().setLatencyMillis(command.getEpochMillis())
                      .build()).subscribe(response -> {
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }, e -> responseObserver.onError(
        Status.INTERNAL.withDescription(e.getMessage()).withCause(e).asRuntimeException()));
  }

}
