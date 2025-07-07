package org.keiron.libraries.web.app.server.grpc;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.springframework.stereotype.Component;

@Component
public class GrpcCallLogger implements ServerInterceptor {

  private static final Metadata.Key<String> META_CORRELATION_ID = Metadata.Key.of("correlation-id",
      Metadata.ASCII_STRING_MARSHALLER);

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
      Metadata headers, ServerCallHandler<ReqT, RespT> next) {

    return null; // TODO
  }

}
