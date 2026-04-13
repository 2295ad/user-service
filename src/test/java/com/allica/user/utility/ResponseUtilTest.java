package com.allica.user.utility;

import com.allica.user.dto.ErrorResponseDTO;
import com.allica.user.dto.UserServiceResponse;
import com.allica.user.utils.ResponseUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseUtilTest {
    @Test
    @DisplayName("success should create a successful response object")
    void success_shouldCreateSuccessfulResponse() {
        String message = "Operation successful";
        String data = "Test Data";

        UserServiceResponse<String> response = ResponseUtil.success(message, data);

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
        assertNull(response.getError());
    }

    @Test
    @DisplayName("failure should create a failure response object")
    void failure_shouldCreateFailureResponse() {
        String message = "Operation failed";
        ErrorResponseDTO error =
                ErrorResponseDTO.builder()
                        .code(HttpStatus.BAD_REQUEST)
                        .message("Invalid input provided")
                        .build();

        UserServiceResponse<Void> response = ResponseUtil.failure(message, error);

        assertNotNull(response);
        assertFalse(response.getSuccess());
        assertEquals(message, response.getMessage());
        assertEquals(error, response.getError());
        assertNull(response.getData());
    }
}
