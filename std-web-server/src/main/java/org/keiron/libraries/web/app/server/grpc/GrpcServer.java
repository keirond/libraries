package org.keiron.libraries.web.app.server.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.grpc.protobuf.services.ProtoReflectionServiceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private final GrpcConnectionTelemetrySvc grpcConnectionTelemetrySvc;
  private final GrpcCallTelemetrySvc grpcCallTelemetrySvc;
  private final GrpcStreamTracerFactory grpcStreamTracerFactory;
  private final GrpcControllerCollector grpcControllerCollector;

  @PostConstruct
  public void start() throws Exception {
    var port = grpcServerConfiguration.getPort();
    var handshakeTimeout = grpcServerConfiguration.getHandshakeTimeout();
    var keepAliveTime = grpcServerConfiguration.getKeepAliveTime();
    var maxConnectionIdle = grpcServerConfiguration.getMaxConnectionIdle();
    var maxConnectionAge = grpcServerConfiguration.getMaxConnectionAge();
    var maxConnectionAgeGrace = grpcServerConfiguration.getMaxConnectionAgeGrace();
    var maxMessageSizeInBytes = grpcServerConfiguration.getMaxMessageSizeInBytes();
    var maxHeaderListSizeInBytes = grpcServerConfiguration.getMaxHeaderListSizeInBytes();

    /*
     to make the grpc use TLS
     var serverCertChain = new ClassPathResource("tls/server.crt").getInputStream();
     var serverPrivateKey = new ClassPathResource("tls/server.key").getInputStream();
    */

    server = ServerBuilder.forPort(port)
                 .handshakeTimeout(handshakeTimeout.toNanos(), TimeUnit.NANOSECONDS)
                 .keepAliveTime(keepAliveTime.toNanos(), TimeUnit.NANOSECONDS)
                 .maxConnectionIdle(maxConnectionIdle.toNanos(), TimeUnit.NANOSECONDS)
                 .maxConnectionAge(maxConnectionAge.toNanos(), TimeUnit.NANOSECONDS)
                 .maxConnectionAgeGrace(maxConnectionAgeGrace.toNanos(), TimeUnit.NANOSECONDS)
                 .maxInboundMessageSize(maxMessageSizeInBytes)
                 .maxInboundMetadataSize(maxHeaderListSizeInBytes)
                 .addTransportFilter(grpcConnectionTelemetrySvc)
                 //.useTransportSecurity(serverCertChain, serverPrivateKey)
                 .intercept(grpcCallTelemetrySvc)
                 .addServices(grpcControllerCollector.getServiceDefinitions())
                 .addService(ProtoReflectionService.newInstance()) // for using Postman only
                 .addService(ProtoReflectionServiceV1.newInstance())
                 .addStreamTracerFactory(grpcStreamTracerFactory).directExecutor().build();

    server.start();
    log.info("Netty started on port {} (grpc)", port);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      log.info("Shutting down gRPC grpc...");
      try {
        GrpcServer.this.stop();
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

}
