package org.keiron.libraries.web.app.server.grpc;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Service
public class GrpcCallTelemetrySvc implements ServerInterceptor {

  private static final Metadata.Key<String> META_CORRELATION_ID = Metadata.Key.of("correlation-id",
      Metadata.ASCII_STRING_MARSHALLER);

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
      Metadata headers, ServerCallHandler<ReqT, RespT> next) {
    var startTime = Instant.now();
    var descriptor = call.getMethodDescriptor();
    var serviceName = descriptor.getServiceName();
    var methodType = descriptor.getType();
    var methodName = descriptor.getFullMethodName();
    var correlationId = headers.get(META_CORRELATION_ID);

    log.info("Incoming grpc.request with service: {}, type: {}, and name: {}", serviceName,
        methodType, methodName);

    var wrappedCall = new ForwardingServerCall.SimpleForwardingServerCall<>(call) {
      @Override
      public void close(Status status, Metadata trailers) {
        var statusCode = status.getCode();

        log.info(
            "Closed grpc.request with service: {}, type: {}, name: {}, response.status_code: {}, and duration: {}ms",
            serviceName, methodType, methodName, statusCode,
            Duration.between(startTime, Instant.now()).toMillis());

        super.close(status, trailers);
      }
    };

    return next.startCall(wrappedCall, headers);
  }

}
