package com.allica.user.http.filters;

import com.allica.user.dto.ErrorResponseDTO;
import com.allica.user.dto.UserServiceResponse;
import com.allica.user.dto.constants.ServiceConstants;
import com.allica.user.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(2)
public class TokenFilter extends OncePerRequestFilter {

  @Value("${security.api-key}")
  private String validApiKey;

  @Autowired private ObjectMapper objectMapper;

  public static final String X_API_KEY = ServiceConstants.X_API_KEY;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return request.getRequestURI().contains("actuator");
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String apiKey = request.getHeader(X_API_KEY);
    if (Objects.nonNull(apiKey) && apiKey.equalsIgnoreCase(validApiKey)) {
      filterChain.doFilter(request, response);
    } else {

      ErrorResponseDTO errorResponseDto =
          ErrorResponseDTO.builder().code(HttpStatus.UNAUTHORIZED).message("unauthorized").build();
      UserServiceResponse<Object> paymentServiceResponse =
          ResponseUtil.failure("Invalid API key", errorResponseDto);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      String json = objectMapper.writeValueAsString(paymentServiceResponse);
      response.getWriter().write(json);
      response.getWriter().flush();
      return;
    }
  }
}
