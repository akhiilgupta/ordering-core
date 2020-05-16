package org.ordering.core.controllers;

import javax.validation.Valid;
import org.ordering.core.requests.CreateOrderRequest;
import org.ordering.core.responses.CreateOrderResponse;
import org.ordering.core.responses.OrderStatusResponse;
import org.ordering.core.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/orders")
public class OrdersController {

  @Autowired
  private OrdersService ordersService;

  @PostMapping
  public CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
    return ordersService.createOrder(request);
  }

  @GetMapping("/{order_id}")
  public OrderStatusResponse getStatus(
      @PathVariable(value = "order_id", required = true) final String orderId) {
    return ordersService.getOrderStatus(orderId);
  }

  @DeleteMapping("/{order_id}")
  public OrderStatusResponse cancelOrder(
      @PathVariable(value = "order_id", required = true) final String orderId) {
    return ordersService.cancelOrder(orderId);
  }


}
