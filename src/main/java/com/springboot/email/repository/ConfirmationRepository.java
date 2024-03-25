package com.springboot.email.repository;

import com.springboot.email.domain.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<Confirmation,Long> {
  Confirmation findByToken(String token);
}
