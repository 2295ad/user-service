package com.allica.user.exception.handler;

import com.allica.user.dto.ErrorResponseDTO;
import com.allica.user.dto.UserServiceResponse;
import com.allica.user.exception.UserServiceException;
import com.allica.user.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<UserServiceResponse<Object>> handlePaymentServiceException(
            UserServiceException ex, WebRequest request) {
        ErrorResponseDTO error =
                ErrorResponseDTO.builder().code(ex.getHttpStatus()).message(ex.getMessage()).build();
        UserServiceResponse<Object> response = ResponseUtil.failure(ex.getMessage(), error);
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserServiceResponse<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.info("validation exception occurred {}", ex);
        BindingResult result = ex.getBindingResult();
        String errorMessages =
                result.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .collect(Collectors.joining(","));
        ErrorResponseDTO error =
                ErrorResponseDTO.builder().code(HttpStatus.BAD_REQUEST).message(errorMessages).build();
        UserServiceResponse<Object> response = ResponseUtil.failure(ex.getTypeMessageCode(), error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<UserServiceResponse<Object>> handleOptimisticLockingException(
            ObjectOptimisticLockingFailureException e) {
        log.info("optimistic locking exception occurred {}", e);
        ErrorResponseDTO errorResponseDTO =
                ErrorResponseDTO.builder()
                        .code(HttpStatus.CONFLICT)
                        .message("Conflict detected: " + e.getMessage())
                        .build();
        UserServiceResponse<Object> response =
                ResponseUtil.failure(e.getMessage(), errorResponseDTO);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<UserServiceResponse> handleDataIntegrityValidationException(
            DataIntegrityViolationException e) {
        log.error("data integrity exception occurred {} {}", e, e.getMostSpecificCause());
        ErrorResponseDTO errorResponseDTO =
                ErrorResponseDTO.builder()
                        .code(HttpStatus.BAD_REQUEST)
                        .message(e.getMostSpecificCause().getMessage())
                        .build();
        UserServiceResponse<Object> response =
                ResponseUtil.failure(e.getMostSpecificCause().getMessage(), errorResponseDTO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
