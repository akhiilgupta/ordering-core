package org.ordering.core.interfaces;

import org.ordering.core.entities.Order;

public interface NotificationService {

  /**
   * Method signature to send notification to the concerned user
   * 
   * @param order
   * @return
   */

  public void sendNotifiation(Order order);
}
