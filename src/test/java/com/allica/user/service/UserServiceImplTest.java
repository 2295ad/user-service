package com.allica.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.allica.user.dto.UserDTO;
import com.allica.user.dto.requests.UserRegistrationRequest;
import com.allica.user.exception.UserServiceException;
import com.allica.user.persistence.entity.UserEntity;
import com.allica.user.persistence.entity.mapper.UserEntityMapper;
import com.allica.user.service.impl.UserServiceImpl;
import com.allica.user.service.transactions.UserTransactions;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserTransactions userTransactions;

  @Mock private UserEntityMapper userEntityMapper;

  @InjectMocks private UserServiceImpl userService;

  @Test
  void testSaveUser() {
    UserRegistrationRequest request = new UserRegistrationRequest();
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setDateOfBirth(LocalDate.of(1995, 8, 15));

    UserEntity entity = new UserEntity();

    when(userEntityMapper.toEntity(any(UserDTO.class))).thenReturn(entity);

    userService.save(request);

    verify(userEntityMapper, times(1)).toEntity(any(UserDTO.class));
    verify(userTransactions, times(1)).save(entity);
  }

  @Test
  void testFetchUser_success() {
    String name = "john";

    UserEntity entity = new UserEntity();
    UserDTO dto = new UserDTO();

    when(userTransactions.fetchUser(name)).thenReturn(List.of(entity));
    when(userEntityMapper.toDto(List.of(entity))).thenReturn(List.of(dto));

    List<UserDTO> result = userService.fetchUser(name);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(userTransactions).fetchUser(name);
    verify(userEntityMapper).toDto(List.of(entity));
  }

  @Test
  void testFetchUser_noData() {
    String name = "unknown";

    when(userTransactions.fetchUser(name)).thenReturn(Collections.emptyList());

    UserServiceException exception =
        assertThrows(UserServiceException.class, () -> userService.fetchUser(name));

    assertTrue(exception.getMessage().contains(name));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
  }

  @Test
  void testFetchUsers() {
    int offset = 0;
    int limit = 10;

    UserEntity entity = new UserEntity();
    UserDTO dto = new UserDTO();

    when(userTransactions.fetchUsers(offset, limit)).thenReturn(List.of(entity));
    when(userEntityMapper.toDto(List.of(entity))).thenReturn(List.of(dto));

    List<UserDTO> result = userService.fetchUsers(offset, limit);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(userTransactions).fetchUsers(offset, limit);
    verify(userEntityMapper).toDto(List.of(entity));
  }
}
