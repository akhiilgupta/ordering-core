package org.ordering.core.responses;

import org.ordering.core.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateDeliveryResponse {

  private String deliveryId;

  @JsonProperty("status")
  private OrderStatus status;

}
