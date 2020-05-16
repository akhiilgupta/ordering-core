package org.ordering.core.entities;

import org.apache.commons.lang3.RandomStringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Builder.Default
  private String profileId = RandomStringUtils.randomNumeric(8);

  private String name;

  private String email;

  private String phone;

  private String address;

}
