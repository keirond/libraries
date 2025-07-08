package org.keiron.libraries.web.app.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FooEntity {

  private String id;
  private String name;
  private String description;
  private int value;
  private long timestamp;

}
