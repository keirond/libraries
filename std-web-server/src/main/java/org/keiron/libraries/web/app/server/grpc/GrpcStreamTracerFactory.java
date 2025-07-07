package org.keiron.libraries.web.app.server.grpc;

import io.grpc.Metadata;
import io.grpc.ServerStreamTracer;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
public class GrpcStreamTracerFactory extends ServerStreamTracer.Factory {

  @Override
  public ServerStreamTracer newServerStreamTracer(String fullMethodName, Metadata headers) {
    return new ServerStreamTracer() {

      private final Instant startTime = Instant.now();

      @Override
      public void streamClosed(Status status) {
        var duration = Duration.between(startTime, Instant.now());
        // TODO
      }
    };
  }

}
