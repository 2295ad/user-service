package com.allica.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserServiceResponse <T>{
    private Boolean success;
    private String message;
    private ErrorResponseDTO error;
    private T data;
}
