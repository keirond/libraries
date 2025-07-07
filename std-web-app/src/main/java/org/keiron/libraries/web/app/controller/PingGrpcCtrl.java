package org.keiron.libraries.web.app.controller;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import org.keiron.libraries.web.app.server.grpc.GrpcController;
import org.keiron.libraries.web.app.service.StdPingSvc;
import org.keiron.proto.ping.v1.Ping;
import org.keiron.proto.ping.v1.PingSvcGrpc;

@GrpcController
@RequiredArgsConstructor
public class PingGrpcCtrl extends PingSvcGrpc.PingSvcImplBase {

  private final StdPingSvc pingSvc;

  @Override
  public void ping(Ping.PingReq request, StreamObserver<Ping.PingRes> responseObserver) {
    try {
      PingReq command = new PingReq().setReqEpochMillis(request.getReqEpochMillis());
      PingRes result = pingSvc.ping(command);
      var resBuilder = Ping.PingRes.newBuilder().setReqEpochMillis(result.getResEpochMillis())
                           .setResEpochMillis(result.getResEpochMillis());
      responseObserver.onNext(resBuilder.build());
    } catch (Exception e) {
      responseObserver.onError(
          Status.INTERNAL.withDescription(e.getMessage()).withCause(e).asRuntimeException());
    }
    responseObserver.onCompleted();
  }

  @Override
  public void pingRx(Ping.PingReq request, StreamObserver<Ping.PingRes> responseObserver) {
    PingReq command = new PingReq().setReqEpochMillis(request.getReqEpochMillis());
    pingSvc.pingRx(command).map(
            result -> Ping.PingRes.newBuilder().setReqEpochMillis(command.getReqEpochMillis())
                          .setResEpochMillis(result.getResEpochMillis()).build())
        .subscribe(response -> {
          responseObserver.onNext(response);
          responseObserver.onCompleted();
        }, e -> responseObserver.onError(
            Status.INTERNAL.withDescription(e.getMessage()).withCause(e).asRuntimeException()));
  }

}
