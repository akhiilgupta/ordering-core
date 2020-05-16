package org.ordering.core.requests;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateOrderRequest {

  private int numberOfItems;

  private String itemId;

  private String name;

  @NotNull
  private String phone;

  private String email;

  @NotNull
  private String address;
}
