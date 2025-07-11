package org.keiron.libraries.web.app.server.grpc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "server.grpc")
public class GrpcServerConfiguration {

  /**
   * Internal-accessible server gRPC port.
   */
  private Integer port = 9090;

  /**
   * The permitted time for new connections to complete negotiation handshakes before being killed.
   */
  private Duration handshakeTimeout = Duration.ofMinutes(2);

  /**
   * The time without read activity before sending a keepalive ping.
   */
  private Duration keepAliveTime = Duration.ofHours(2);

  /**
   * The time waiting for read activity after sending a keepalive ping.
   */
  private Duration keepAliveTimeout = Duration.ofSeconds(20);

  /**
   * The maximum connection idle time, connections being idle for longer than which will be
   * gracefully terminated.
   */
  private Duration maxConnectionIdle = Duration.ofNanos(Long.MAX_VALUE);

  /**
   * The maximum connection age, connections lasting longer than which will be gracefully
   * terminated.
   */
  private Duration maxConnectionAge = Duration.ofNanos(Long.MAX_VALUE);

  /**
   * The grace time for the graceful connection termination. Once the max connection age is reached,
   * RPCs have the grace time to complete. RPCs that do not complete in time will be cancelled,
   * allowing the connection to terminate.
   */
  private Duration maxConnectionAgeGrace = Duration.ofNanos(Long.MAX_VALUE);

  /**
   * The maximum message size allowed to be received on the grpc. Defaults to 4MiB.
   */
  private Integer maxMessageSizeInBytes = 4194304;

  /**
   * The maximum size of metadata allowed to be received. Defaults to 8KiB
   */
  private Integer maxHeaderListSizeInBytes = 8192;

  /**
   * Enabled mTLS for zero-trust network in internal microservices.
   */
  private Boolean enableMtls = false;
  private String certificateChain = "classpath:certs/server.crt";
  private String privateKey = "classpath:certs/server.key";
  private String trustCertCollection = "classpath:certs/ca.crt";
  private Boolean clientAuthRequired = true;

}
