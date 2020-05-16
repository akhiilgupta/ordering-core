package org.ordering.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  
  private String profileId;
  
  private String name;
  
  private String email;
  
  private String phone;
  
  private String address;

  public User(UserEntity user) {
    this.profileId = user.getProfileId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.phone = user.getPhone();
    this.address = user.getAddress();
  }
}
