package org.ordering.core.entities;

import java.time.LocalDateTime;
import org.ordering.core.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Order {

  public Order(OrderEntity order) {
    this.orderId = order.getOrderId();
    this.status = order.getStatus();
    this.numberOfItems = order.getNumberOfItems();
    this.itemId = order.getItemId();
    this.deliveryId = order.getDeliveryId();
    this.createdAt = order.getCreatedAt();
    this.orderedBy = new User(order.getOrderedBy());
  }

  private String orderId;

  private OrderStatus status;

  private int numberOfItems;

  private String itemId;

  private User orderedBy;

  private String deliveryId;

  private LocalDateTime createdAt;

}
