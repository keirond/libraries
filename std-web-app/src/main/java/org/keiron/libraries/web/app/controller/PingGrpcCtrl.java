package org.keiron.libraries.web.app.controller;

import io.grpc.stub.StreamObserver;
import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import org.keiron.proto.ping.v1.Ping;
import org.keiron.proto.ping.v1.PingSvcGrpc;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
public class PingGrpcCtrl extends PingSvcGrpc.PingSvcImplBase {

  @Override
  public void ping(Ping.PingReq request, StreamObserver<Ping.PingRes> responseObserver) {
    try {
      PingReq command = new PingReq().setReqEpochMillis(request.getReqEpochMillis());
      PingRes result = new PingRes().setReqEpochMillis(command.getReqEpochMillis())
                           .setResEpochMillis(Instant.now().toEpochMilli());
      var resBuilder = Ping.PingRes.newBuilder().setResEpochMillis(result.getResEpochMillis())
                           .setResEpochMillis(result.getResEpochMillis());
      responseObserver.onNext(resBuilder.build());
    } catch (Exception e) {
      responseObserver.onError(e);
    }
    responseObserver.onCompleted();
  }

}
