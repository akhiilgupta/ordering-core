package org.ordering.core.entities;

/*
 * Data Access Object to maintain all the orders
 */

import java.time.LocalDateTime;
import org.apache.commons.lang3.RandomStringUtils;
import org.ordering.core.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

  @Builder.Default
  private String orderId = RandomStringUtils.randomAlphanumeric(10);

  @Builder.Default
  private OrderStatus status = OrderStatus.PENDING;

  private int numberOfItems;

  private String itemId;

  private UserEntity orderedBy;

  private String deliveryId;

  private LocalDateTime createdAt;

}
