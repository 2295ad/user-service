package com.allica.user.service;

import com.allica.user.dto.UserDTO;
import com.allica.user.dto.requests.UserRegistrationRequest;
import java.util.List;

public interface UserService {

  void save(UserRegistrationRequest userRegistrationRequest);

  List<UserDTO> fetchUser(String name);

  List<UserDTO> fetchUsers(int offset, int limit);
}
