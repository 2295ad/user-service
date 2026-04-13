package com.allica.user.persistence.repository;

import com.allica.user.persistence.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  @Query(value = "SELECT * FROM users LIMIT :limit OFFSET :offset", nativeQuery = true)
  List<UserEntity> findUsersWithOffset(@Param("offset") int offset, @Param("limit") int limit);

  @Query(
      "SELECT u FROM UserEntity u "
          + "WHERE LOWER(u.firstName) = LOWER(:name) "
          + "OR LOWER(u.lastName) = LOWER(:name)")
  List<UserEntity> findUsers(@Param("name") String name);
}
