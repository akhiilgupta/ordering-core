package org.ordering.core.interfaces;

import java.util.Optional;
import org.ordering.core.entities.User;
import org.ordering.core.enums.OrderStatus;
import org.ordering.core.responses.CreateDeliveryResponse;
import org.ordering.core.responses.DeliveryStatusResponse;

public interface DeliveryService {

  /**
   * Method to delegate an order to a delivery person for a given address
   * 
   * @param orderId
   * @param address
   * @return
   */

  public Optional<CreateDeliveryResponse> createDelivery(String orderId, User user);

  /**
   * Get delivery status of the order
   * 
   * @param deliveryId
   * @return
   */

  public Optional<DeliveryStatusResponse> getStatus(String deliveryId);

  /**
   * Update status of the delivery if already accepted by the delivery service
   * 
   * @param failed
   * @return
   */

  public Optional<DeliveryStatusResponse> updateStatus(String deliveryId, OrderStatus status);
}
