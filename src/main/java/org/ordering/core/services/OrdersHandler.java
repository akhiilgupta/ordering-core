package org.ordering.core.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.ordering.core.entities.Order;
import org.ordering.core.entities.OrderEntity;
import org.ordering.core.entities.UserEntity;
import org.ordering.core.enums.OrderStatus;
import org.ordering.core.requests.CreateOrderRequest;
import org.springframework.stereotype.Component;

@Component
public class OrdersHandler {
  private static final List<OrderEntity> ordersList =
      Collections.synchronizedList(new ArrayList<>());

  /**
   * Create order entity and store it in the list in synchronous manner
   * 
   * @param request
   * @return
   */

  public Order createOrder(CreateOrderRequest request) {
    OrderEntity order = OrderEntity.builder().itemId(request.getItemId())
        .orderedBy(UserEntity.builder().address(request.getAddress()).email(request.getEmail())
            .name(request.getName()).email(request.getEmail()).build())
        .numberOfItems(request.getNumberOfItems()).createdAt(LocalDateTime.now()).build();
    ordersList.add(order);
    return new Order(order);
  }

  public Optional<Order> getOrderById(String orderId) {
    synchronized (ordersList) {
      return ordersList.stream().filter(o -> o.getOrderId().equals(orderId)).findAny()
          .map(Order::new);
    }
  }

  /**
   * Update order entity in the orders list
   * 
   * @param order
   * @return
   */

  public Optional<Order> updateOrder(Order order) {
    synchronized (ordersList) {
      for (OrderEntity orderEntity : ordersList) {
        if (orderEntity.getOrderId().equals(order.getOrderId())) {
          orderEntity.setStatus(order.getStatus());
          orderEntity.setDeliveryId(order.getDeliveryId());
          orderEntity.setNumberOfItems(order.getNumberOfItems());
          orderEntity.setItemId(order.getItemId());
          return Optional.of(order);
        }
      }
      return Optional.empty();
    }
  }

  /**
   * Get a list of all the orders by order status.
   * 
   * @param status {@link OrderStatus}
   * @param size max size of the result
   * @return
   */

  public List<Order> getOrdersByStatus(OrderStatus status, int size) {
    synchronized (ordersList) {
      return ordersList.stream().filter(o -> o.getStatus().equals(status)).limit(size)
          .map(Order::new).collect(Collectors.toList());
    }
  }

}
