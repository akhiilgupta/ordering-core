package org.ordering.core.services;

import org.ordering.core.entities.Order;
import org.ordering.core.interfaces.NotificationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceImpl implements NotificationService {

  /**
   * Send notifications to the customer in asynchronus manner using daemon thread pool excecutor
   * 
   * @param order
   * @return booolean
   */

  @Async("threadPoolTaskExecutor")
  public void sendNotification(Order order) {
    // logic to send notification to the user on status change
  }
}
