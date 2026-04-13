package com.allica.user.utils;

import com.allica.user.dto.ErrorResponseDTO;
import com.allica.user.dto.UserServiceResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtil {
  public <T> UserServiceResponse<T> success(String message, T data) {
    return new UserServiceResponse<>(Boolean.TRUE, message, null, data);
  }

  public <T> UserServiceResponse<T> failure(String message, ErrorResponseDTO error) {
    return new UserServiceResponse<>(Boolean.FALSE, message, error, null);
  }
}
