package org.ordering.core.enums;

import java.util.Arrays;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
  PENDING(0, new HashSet<>(Arrays.asList())),
  ACCEPTED(1, new HashSet<>(Arrays.asList("ACCEPTED", "ONROUTE"))),
  FAILED(2, new HashSet<>(Arrays.asList("FAILED", "CANCELLED"))),
  DELIVERED(3, new HashSet<>(Arrays.asList("DELIVERED")));

  private int code;
  private HashSet<String> status;

  @JsonCreator
  public static OrderStatus setStatus(String status) {
    return Arrays.stream(OrderStatus.values()).filter(s -> s.status.contains(status)).findAny()
        .orElse(OrderStatus.PENDING);
  }
}
