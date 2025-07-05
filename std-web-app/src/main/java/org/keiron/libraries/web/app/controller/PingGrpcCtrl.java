package org.keiron.libraries.web.app.controller;

import io.grpc.stub.StreamObserver;
import org.keiron.proto.ping.v1.Ping;
import org.keiron.proto.ping.v1.PingSvcGrpc;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingGrpcCtrl extends PingSvcGrpc.PingSvcImplBase {

  @Override
  public void ping(Ping.PingReq request, StreamObserver<Ping.PingRes> responseObserver) {

  }

}
