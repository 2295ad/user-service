package com.allica.user.http.controller;

import static com.allica.user.dto.constants.ServiceConstants.SUCCESS;

import com.allica.user.dto.UserDTO;
import com.allica.user.dto.UserServiceResponse;
import com.allica.user.dto.requests.UserRegistrationRequest;
import com.allica.user.service.UserService;
import com.allica.user.utils.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping(value = "/v1/register")
  public UserServiceResponse<?> register(
      @RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
    userService.save(userRegistrationRequest);
    return ResponseUtil.success(SUCCESS, null);
  }

  @GetMapping(value = "/v1/{name}")
  public UserServiceResponse<List<UserDTO>> fetchUser(@PathVariable @NotBlank String name) {
    List<UserDTO> userDTOs = userService.fetchUser(name);
    return ResponseUtil.success(SUCCESS, userDTOs);
  }

  @GetMapping(value = "/v1/find-all")
  public UserServiceResponse<List<UserDTO>> findAll(
      @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit) {
    List<UserDTO> userDTOs = userService.fetchUsers(offset, limit);
    return ResponseUtil.success(SUCCESS, userDTOs);
  }
}
