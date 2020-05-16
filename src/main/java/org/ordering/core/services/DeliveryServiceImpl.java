package org.ordering.core.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.ordering.core.entities.User;
import org.ordering.core.enums.OrderStatus;
import org.ordering.core.interfaces.DeliveryService;
import org.ordering.core.requests.CreateDeliveryRequest;
import org.ordering.core.requests.UpdateDeliveryRequest;
import org.ordering.core.responses.CreateDeliveryResponse;
import org.ordering.core.responses.DeliveryStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class DeliveryServiceImpl implements DeliveryService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${delivery.service.base-url}")
  private String deliveryBaseURL;

  /**
   * Calls delivery service to delegate order to a delivery person based on availability
   * 
   * @param orderId
   * @param user
   * @return
   */

  public Optional<CreateDeliveryResponse> createDelivery(String orderId, User user) {

    HttpEntity<CreateDeliveryRequest> requestEntity = new HttpEntity<>(
        CreateDeliveryRequest.builder().orderId(orderId).address(user.getAddress()).build());
    try {
      ResponseEntity<CreateDeliveryResponse> response =
          restTemplate.exchange(deliveryBaseURL + "/v1/delivery", HttpMethod.POST, requestEntity,
              CreateDeliveryResponse.class);
      return Optional.ofNullable(response.getBody());
    } catch (RestClientException e) {
      return Optional.empty();
    }
  }

  /**
   * Calls delivery service to fetch the status of on going delivery
   * 
   * @param deliveryId
   * @return
   */

  @Override
  public Optional<DeliveryStatusResponse> getStatus(String deliveryId) {
    Map<String, String> uriVar = new HashMap<>();
    uriVar.put("delivery_id", deliveryId);
    try {
      ResponseEntity<DeliveryStatusResponse> response =
          restTemplate.exchange(deliveryBaseURL + "/v1/delivery/{delivery_id}", HttpMethod.GET,
              null, DeliveryStatusResponse.class, uriVar);
      return Optional.ofNullable(response.getBody());
    } catch (RestClientException e) {
      return Optional.empty();
    }
  }

  /**
   * Calls delivery service to update the status of the deliuvery
   * 
   * @param status
   * @return
   */

  @Override
  public Optional<DeliveryStatusResponse> updateStatus(String deliveryId, OrderStatus status) {
    Map<String, String> uriVar = new HashMap<>();
    uriVar.put("delivery_id", deliveryId);
    HttpEntity<UpdateDeliveryRequest> requestEntity = new HttpEntity<>(
        UpdateDeliveryRequest.builder().deliveryId(deliveryId).status("CANCELLED").build());

    try {
      ResponseEntity<DeliveryStatusResponse> response =
          restTemplate.exchange(deliveryBaseURL + "/v1/delivery/{delivery_id}", HttpMethod.PUT,
              requestEntity, DeliveryStatusResponse.class, uriVar);
      return Optional.ofNullable(response.getBody());
    } catch (RestClientException e) {
      return Optional.empty();
    }
  }

}
