package org.keiron.libraries.web.app.server.grpc;

import io.grpc.Attributes;
import io.grpc.Grpc;
import io.grpc.ServerTransportFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
public class GrpcConnectionLogger extends ServerTransportFilter {

  private static final Attributes.Key<Instant> ATTR_CONNECTION_START_TIME = Attributes.Key.create(
      "connection-start-time");

  @Override
  public Attributes transportReady(Attributes transportAttrs) {
    SocketAddress peerAddress = transportAttrs.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
    var startTime = Instant.now();
    log.info("Connection ready, peerAddress={} at {}", peerAddress, startTime);
    return transportAttrs.toBuilder().set(ATTR_CONNECTION_START_TIME, startTime).build();
  }

  @Override
  public void transportTerminated(Attributes transportAttrs) {
    if (transportAttrs == null) {
      log.warn("Connection closed but transport attributes were null");
      return;
    }

    SocketAddress peerAddress = transportAttrs.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
    Instant startTime = transportAttrs.get(ATTR_CONNECTION_START_TIME);
    var endTime = Instant.now();
    if (startTime != null) {
      var lifetime = Duration.between(startTime, endTime);
      log.info("Connection terminated, peerAddress={}, connectedAt={}, closedAt={}, lifetime={}ms",
          peerAddress, startTime, endTime, lifetime.toMillis());
    } else {
      log.warn("Connection closed, peerAddress={}, but connect time missing", peerAddress);
    }
  }

}
