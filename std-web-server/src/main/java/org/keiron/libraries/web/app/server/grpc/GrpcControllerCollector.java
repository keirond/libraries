package org.keiron.libraries.web.app.server.grpc;

import io.grpc.BindableService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GrpcControllerCollector implements BeanPostProcessor {

  @Getter
  private final List<BindableService> serviceDefinitions = new ArrayList<>();

  @Override
  public Object postProcessBeforeInitialization(Object bean, @Nullable String beanName)
      throws BeansException {
    if (bean.getClass().isAnnotationPresent(GrpcController.class)) {
      if (bean instanceof BindableService) {
        serviceDefinitions.add((BindableService) bean);
        log.info("Found gRPC controller: {}", bean.getClass().getName());
      } else {
        log.warn("Found gRPC controller as unknown BindableService: {}", bean.getClass().getName());
      }
    }
    return bean;
  }

}
