package com.allica.user.http.controller;

import static com.allica.user.dto.constants.ServiceConstants.SUCCESS;

import com.allica.user.dto.UserDTO;
import com.allica.user.dto.UserServiceResponse;
import com.allica.user.dto.requests.UserRegistrationRequest;
import com.allica.user.service.UserService;
import com.allica.user.utils.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping(value = "/v1/register")
  public UserServiceResponse<?> initiatePayout(
      @RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
    userService.save(userRegistrationRequest);
    return ResponseUtil.success(SUCCESS, payoutTransactionDTO);
  }

  @GetMapping(value = "/v1/status/{id}")
  public PaymentServiceResponse<UserDTO> fetchPayoutStatus(
      @PathVariable @NotBlank String transactionId) {
    PayoutTransactionDTO payoutStatusDTO = userService.f(transactionId);
    return ResponseUtil.success(SUCCESS, payoutStatusDTO);
  }
}
