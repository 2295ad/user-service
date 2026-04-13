package com.allica.user.service.impl;

import com.allica.user.dto.UserDTO;
import com.allica.user.dto.requests.UserRegistrationRequest;
import com.allica.user.exception.UserServiceException;
import com.allica.user.persistence.entity.UserEntity;
import com.allica.user.persistence.entity.mapper.UserEntityMapper;
import com.allica.user.service.UserService;
import com.allica.user.service.transactions.UserTransactions;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserTransactions userTransactions;
  private final UserEntityMapper userEntityMapper;

  @Override
  public void save(UserRegistrationRequest userRegistrationRequest) {
    UserDTO userDTO =
        UserDTO.builder()
            .dateOfBirth(userRegistrationRequest.getDateOfBirth())
            .firstName(userRegistrationRequest.getFirstName())
            .lastName(userRegistrationRequest.getLastName())
            .build();
    UserEntity userEntity = userEntityMapper.toEntity(userDTO);
    userTransactions.save(userEntity);
  }

  @Override
  public List<UserDTO> fetchUser(String name) {
    List<UserEntity> user = userTransactions.fetchUser(name);
    if (user.isEmpty()) {
      log.info("No user details present for name {}", name);
      throw new UserServiceException(
          "No user details present for name -" + name, HttpStatus.BAD_REQUEST);
    }
    return userEntityMapper.toDto(user);
  }

  @Override
  public List<UserDTO> fetchUsers(int offset, int limit) {
    List<UserEntity> userList = userTransactions.fetchUsers(offset, limit);
    return userEntityMapper.toDto(userList);
  }
}
