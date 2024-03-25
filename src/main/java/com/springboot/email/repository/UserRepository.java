package com.springboot.email.repository;

import com.springboot.email.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
  User findByEmailIgnoreCase(String email);

  Boolean existsByEmail(String email);
}
