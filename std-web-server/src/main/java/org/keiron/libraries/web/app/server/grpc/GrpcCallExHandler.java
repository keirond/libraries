package org.keiron.libraries.web.app.server.grpc;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GrpcCallExHandler implements ServerInterceptor {

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
      Metadata headers, ServerCallHandler<ReqT, RespT> next) {
    ServerCall.Listener<ReqT> listener = next.startCall(call, headers);
    return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(listener) {
      @Override
      public void onHalfClose() {
        try {
          super.onHalfClose();
        } catch (Exception e) {
          handle(e, call);
        }
      }
    };
  }

  private <ReqT, RespT> void handle(Exception e, ServerCall<ReqT, RespT> call) {
    Status status = switch (e) {
      case IllegalArgumentException iae ->
          Status.INVALID_ARGUMENT.withDescription(iae.getMessage());
      default -> {
        log.error("Exception caught in gRPC handler: {}", e.getMessage(), e);
        yield Status.INTERNAL.withDescription(e.getMessage());
      }
    };
    call.close(status, new Metadata());
  }

}
