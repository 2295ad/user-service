package com.allica.user.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRegistrationRequest {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank private String dateOfBirth;
}
