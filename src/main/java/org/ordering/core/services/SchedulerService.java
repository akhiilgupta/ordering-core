package org.ordering.core.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.ordering.core.entities.Order;
import org.ordering.core.enums.OrderStatus;
import org.ordering.core.interfaces.DeliveryService;
import org.ordering.core.interfaces.NotificationService;
import org.ordering.core.responses.CreateDeliveryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SchedulerService {

  @Autowired
  private OrdersHandler ordersHandler;

  @Autowired
  private DeliveryService deliveryService;

  @Autowired
  private NotificationService notificationService;

  @Value("${order.delivery.delegation.timeout}")
  private Long timeout;

  /**
   * Cron Job to delegate all the pending orders int the system and to mark the orders as failed in
   * case timeout limit is reached. If there is a change in order status then a notification is sent
   * to the user.
   */

  @Scheduled(fixedRate = 200000)
  public void delegatePendingOrders() {
    List<Order> orders = ordersHandler.getOrdersByStatus(OrderStatus.PENDING, 20);
    log.info("cron job for delegating pending orders count: {}", orders.size());
    for (Order order : orders) {
      if (LocalDateTime.now().minusSeconds(timeout).isAfter(order.getCreatedAt())) {
        order.setStatus(OrderStatus.FAILED);
        ordersHandler.updateOrder(order);
      } else {
        Optional<CreateDeliveryResponse> createDelivery =
            deliveryService.createDelivery(order.getOrderId(), order.getOrderedBy());
        if (createDelivery.isPresent()
            && StringUtils.isNotBlank(createDelivery.get().getDeliveryId())) {
          order.setStatus(OrderStatus.ACCEPTED);
          order.setDeliveryId(createDelivery.get().getDeliveryId());
          ordersHandler.updateOrder(order);
        }
      }
      if (!OrderStatus.PENDING.equals(order.getStatus())) {
        notificationService.sendNotifiation(order);
      }
    }
  }
}
