package com.allica.user.dto.requests;

import jakarta.validation.constraints.NotBlank;

public class UserRegistrationRequest {
  @NotBlank private String firstName;
  @NotBlank private String lastName;
  @NotBlank private String dateOfBirth;
}
