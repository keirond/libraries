package org.keiron.libraries.web.app.grpc;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface GrpcController {

  /**
   * Alias for {@link Controller#value}.
   */
  @AliasFor(annotation = Controller.class) String value() default "";

}
