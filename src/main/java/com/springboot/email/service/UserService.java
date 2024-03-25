package com.springboot.email.service;

import com.springboot.email.domain.User;

public interface UserService {
  User saveUser(User user);

  Boolean verifyToken(String token);
}
