package org.keiron.libraries.web.app.server.grpc;

import io.grpc.BindableService;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.grpc.protobuf.services.ProtoReflectionServiceV1;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GrpcProtoReflectionSvcFactory {

  @Bean
  @Profile("dev")
  @Qualifier("protoReflectionService")
  public BindableService protoReflectionServiceDev() {
    return ProtoReflectionService.newInstance();
  }

  @Bean
  @Profile("prod")
  @Qualifier("protoReflectionService")
  public BindableService protoReflectionServiceProd() {
    return ProtoReflectionServiceV1.newInstance();
  }

}
