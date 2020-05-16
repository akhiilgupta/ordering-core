package org.ordering.core.responses;

import org.ordering.core.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderStatusResponse {
  
  private OrderStatus status;
  
  private String message;
}
