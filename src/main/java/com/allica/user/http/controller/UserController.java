package com.allica.user.http.controller;

import com.allica.user.dto.UserRegistrationRequest;
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
    public UserServiceResponse<PayoutTransactionDTO> initiatePayout(
            @RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        PayoutTransactionDTO payoutTransactionDTO = payoutService.initiate(payoutInitiationRequest);
        return ResponseUtil.success(TX_INITIATED, payoutTransactionDTO);
    }

    @GetMapping(value = "/v1/status/{transactionId}")
    public PaymentServiceResponse<PayoutTransactionDTO> fetchPayoutStatus(
            @PathVariable @NotBlank String transactionId) {
        PayoutTransactionDTO payoutStatusDTO = payoutService.fetchStatus(transactionId);
        return ResponseUtil.success(SUCCESS, payoutStatusDTO);
    }

}
