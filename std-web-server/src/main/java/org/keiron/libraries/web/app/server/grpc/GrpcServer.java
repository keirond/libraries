package org.keiron.libraries.web.app.server.grpc;

import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class GrpcServer {

  private Server server;

  private final GrpcServerConfiguration grpcServerConfiguration;
  private final GrpcConnectionObserver grpcConnectionObserver;
  private final GrpcCallSecurity grpcCallSecurity;
  private final GrpcCallObserver grpcCallObserver;
  private final GrpcCallExHandler grpcCallExHandler;
  private final GrpcStreamTracerFactory grpcStreamTracerFactory;
  private final GrpcControllerCollector grpcControllerCollector;

  @Qualifier("protoReflectionService")
  private final GrpcProtoReflectionSvcFactory.ReflectionBindableService protoReflectionService;

  @PostConstruct
  public void start() throws Exception {

    /*
     to make the grpc use TLS
     var serverCertChain = new ClassPathResource("tls/server.crt").getInputStream();
     var serverPrivateKey = new ClassPathResource("tls/server.key").getInputStream();
    */

    var port = grpcServerConfiguration.getPort();
    var serverBuilder = buildWithServerConfig(ServerBuilder.forPort(port));
    server = serverBuilder.addTransportFilter(grpcConnectionObserver)
                          //.useTransportSecurity(serverCertChain, serverPrivateKey)
                          .addService(protoReflectionService.bindableService())
                          .addServices(grpcControllerCollector
                              .getServiceDefinitions()
                              .stream()
                              .map(this::wrapWithInterceptors)
                              .toList())
                          .addStreamTracerFactory(grpcStreamTracerFactory)
                          .directExecutor()
                          .build();

    server.start();
    log.info("Netty started on port {} (grpc)", port);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      log.info("Shutting down gRPC grpc...");
      try {
        this.stop();
      } catch (InterruptedException e) {
        log.error("gRPC stopping interrupted with exception {}", e.getMessage(), e);
      }
      log.info("gRPC grpc shutdown gracefully");
    }, "grpc-shutdown"));
  }

  @PreDestroy
  public void stop() throws InterruptedException {
    if (server != null)
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
  }

  private <T extends ServerBuilder<T>> ServerBuilder<T> buildWithServerConfig(
      ServerBuilder<T> current) {
    var handshakeTimeout = grpcServerConfiguration.getHandshakeTimeout();
    var keepAliveTime = grpcServerConfiguration.getKeepAliveTime();
    var maxConnectionIdle = grpcServerConfiguration.getMaxConnectionIdle();
    var maxConnectionAge = grpcServerConfiguration.getMaxConnectionAge();
    var maxConnectionAgeGrace = grpcServerConfiguration.getMaxConnectionAgeGrace();
    var maxMessageSizeInBytes = grpcServerConfiguration.getMaxMessageSizeInBytes();
    var maxHeaderListSizeInBytes = grpcServerConfiguration.getMaxHeaderListSizeInBytes();

    return current
        .handshakeTimeout(handshakeTimeout.toNanos(), TimeUnit.NANOSECONDS)
        .keepAliveTime(keepAliveTime.toNanos(), TimeUnit.NANOSECONDS)
        .maxConnectionIdle(maxConnectionIdle.toNanos(), TimeUnit.NANOSECONDS)
        .maxConnectionAge(maxConnectionAge.toNanos(), TimeUnit.NANOSECONDS)
        .maxConnectionAgeGrace(maxConnectionAgeGrace.toNanos(), TimeUnit.NANOSECONDS)
        .maxInboundMessageSize(maxMessageSizeInBytes)
        .maxInboundMetadataSize(maxHeaderListSizeInBytes);
  }

  private ServerServiceDefinition wrapWithInterceptors(BindableService service) {
    return ServerInterceptors.intercept(service, grpcCallSecurity, grpcCallObserver,
        grpcCallExHandler);
  }

}
