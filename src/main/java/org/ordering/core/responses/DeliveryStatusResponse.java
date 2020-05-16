package org.ordering.core.responses;

import org.ordering.core.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryStatusResponse {
  
  private String message;
  
  @JsonProperty("status")
  private OrderStatus status;
}
