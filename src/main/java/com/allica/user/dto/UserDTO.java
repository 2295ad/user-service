package com.allica.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
  private Integer id;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String createdAt;
  private String updatedAt;
}
