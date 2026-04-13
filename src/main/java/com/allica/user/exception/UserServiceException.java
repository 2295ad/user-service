package com.allica.user.exception;

import org.springframework.http.HttpStatus;

public class UserServiceException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final HttpStatus httpStatus;

  public UserServiceException(String message, Throwable throwable, HttpStatus httpStatus) {
    super(message, throwable);
    this.httpStatus = httpStatus;
  }

  public UserServiceException(String message, HttpStatus httpStatus, Throwable throwable) {
    super(message, throwable);
    this.httpStatus = httpStatus;
  }

  public UserServiceException(String message, Throwable throwable) {
    super(message, throwable);
    this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  }

  public UserServiceException(String message) {
    super(message);
    this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  }

  public UserServiceException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return this.httpStatus;
  }
}
