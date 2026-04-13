package com.allica.user.service.transactions;

import com.allica.user.persistence.entity.UserEntity;
import com.allica.user.persistence.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTransactions {
  private final UserRepository userRepository;

  @Transactional
  public void save(UserEntity userEntity) {
    userRepository.save(userEntity);
  }

  @Transactional(readOnly = true)
  public List<UserEntity> fetchUser(String name) {
    return userRepository.findUsers(name);
  }

  @Transactional(readOnly = true)
  public List<UserEntity> fetchUsers(int offset, int limit) {
    return userRepository.findUsersWithOffset(offset, limit);
  }
}
