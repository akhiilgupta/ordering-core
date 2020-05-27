package org.ordering.core.services;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.ordering.core.constants.OrderConstants;
import org.ordering.core.entities.Order;
import org.ordering.core.enums.OrderStatus;
import org.ordering.core.interfaces.DeliveryService;
import org.ordering.core.interfaces.NotificationService;
import org.ordering.core.requests.CreateOrderRequest;
import org.ordering.core.responses.CreateDeliveryResponse;
import org.ordering.core.responses.CreateOrderResponse;
import org.ordering.core.responses.DeliveryStatusResponse;
import org.ordering.core.responses.OrderStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdersService {

  @Autowired
  private OrdersHandler ordersHandler;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private DeliveryService deliveryService;

  /**
   * Method to create order for a given a user and store it in OrderEntities record. Delegate
   * delivery person to the requested order {@link CreateOrderResponse} and send notification when
   * the order is accepted by a delivery person.
   * 
   * @param request
   * @return CreateOrder
   */

  public CreateOrderResponse createOrder(CreateOrderRequest request) {
    Order order = ordersHandler.createOrder(request);
    Optional<CreateDeliveryResponse> createDelivery =
        deliveryService.createDelivery(order.getOrderId(), order.getOrderedBy());
    if (createDelivery.isPresent()
        && StringUtils.isNotBlank(createDelivery.get().getDeliveryId())) {
      order.setStatus(OrderStatus.ACCEPTED);
      order.setDeliveryId(createDelivery.get().getDeliveryId());
      ordersHandler.updateOrder(order);

      // Send notification to the user if delivery accepted by delivery person
      notificationService.sendNotification(order);
    }
    return CreateOrderResponse.builder().status(order.getStatus()).orderId(order.getOrderId())
        .build();
  }

  /**
   * Method to get status of the order by order Id
   * 
   * @param orderId
   * @return
   */

  public OrderStatusResponse getOrderStatus(String orderId) {
    Optional<Order> orderOptional = ordersHandler.getOrderById(orderId);
    if (orderOptional.isPresent()) {

      Order order = orderOptional.get();
      if (canCallDeliveryService(order)) {

        // Get order status optional from delivery service

        order.setStatus(deliveryService.getStatus(order.getDeliveryId())
            .map(DeliveryStatusResponse::getStatus).orElse(order.getStatus()));
        ordersHandler.updateOrder(order);
      }
      return OrderStatusResponse.builder().message(OrderConstants.SUCCESS).status(order.getStatus())
          .build();
    }
    // return order not found message if order id not present in records
    return OrderStatusResponse.builder().message(OrderConstants.ORDER_NOT_FOUND).build();
  }

  /**
   * Cancel the requested order. If already accepted by the delivery service, Delivery service is
   * called to cancel the order and order is marked failed.
   * 
   * @param orderId
   * @return
   */

  public OrderStatusResponse cancelOrder(String orderId) {
    Optional<Order> orderOptional = ordersHandler.getOrderById(orderId);
    if (orderOptional.isPresent()) {
      Order order = orderOptional.get();
      OrderStatusResponse response = new OrderStatusResponse();
      if (OrderStatus.PENDING.equals(order.getStatus())) {
        order.setStatus(OrderStatus.FAILED);
        response.setStatus(OrderStatus.FAILED);
        response.setMessage(OrderConstants.SUCCESS);
      } else if (OrderStatus.ACCEPTED.equals(order.getStatus())) {
        order.setStatus(deliveryService.updateStatus(order.getDeliveryId(), OrderStatus.FAILED)
            .map(DeliveryStatusResponse::getStatus).orElse(order.getStatus()));
        response.setStatus(order.getStatus());
        response.setMessage(OrderConstants.SUCCESS);
      }
      // Send notification to the user of the current state after cancel attempt.
      notificationService.sendNotification(order);
      ordersHandler.updateOrder(order);
      return response;

    }
    // return order not found message if order id not present in records
    return OrderStatusResponse.builder().message(OrderConstants.ORDER_NOT_FOUND).build();
  }

  private boolean canCallDeliveryService(Order order) {
    return !OrderStatus.DELIVERED.equals(order.getStatus())
        && StringUtils.isNotEmpty(order.getDeliveryId());
  }

}
