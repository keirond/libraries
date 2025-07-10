package org.keiron.libraries.web.app.server.grpc;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.keiron.libraries.web.app.common.JSON;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

@Slf4j
@Service
public class GrpcCallObserver implements ServerInterceptor {

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

    // support unary (1-1)
    // support client streaming (n-1)
    // support server streaming (1-n)
    // support bidirectional streaming (n-m)
    var requests = new ArrayList<>();
    var responses = new ArrayList<>();

    var wrappedCall = new ForwardingServerCall.SimpleForwardingServerCall<>(call) {

      @Override
      public void sendMessage(RespT response) {
        responses.add(JSON.toTruncatedString(response));
        super.sendMessage(response);
      }

      @Override
      public void close(Status status, Metadata trailers) {
        log.info(
            "Closed grpc.request with service: {}, type: {}, name: {}, response.status_code: {}, duration: {}ms, and requests: {}, responses: {}",
            serviceName, methodType, methodName, status.getCode(),
            Duration.between(startTime, Instant.now()).toMillis(), requests, responses);
        super.close(status, trailers);
      }
    };

    ServerCall.Listener<ReqT> listener = next.startCall(wrappedCall, headers);
    return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {
      @Override
      public void onMessage(ReqT request) {
        requests.add(JSON.toTruncatedString(request));
        super.onMessage(request);
      }
    };
  }

}
