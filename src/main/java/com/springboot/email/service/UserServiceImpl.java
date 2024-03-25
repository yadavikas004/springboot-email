package com.springboot.email.service;

import com.springboot.email.domain.Confirmation;
import com.springboot.email.domain.User;
import com.springboot.email.repository.ConfirmationRepository;
import com.springboot.email.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;

  private final ConfirmationRepository confirmationRepository;

  private final EmailService emailService;

  @Override
  public User saveUser(User user) {
    if (userRepository.existsByEmail(user.getEmail())){
      throw new RuntimeException("Email already exist");
    }
    user.setEnabled(false);
    userRepository.save(user);

    var confirmation = new Confirmation(user);
    confirmationRepository.save(confirmation);

    /* TODO Send email to user with token  */
//    emailService.sendSimpleMailMessage(user.getName(),user.getEmail(),confirmation.getToken());
//    emailService.sendMimeMessageWithAttachments(user.getName(),user.getEmail(),confirmation.getToken());
//    emailService.sendMimeMessageWithEmbeddedFiles(user.getName(),user.getEmail(),confirmation.getToken());
    emailService.sendHtmlEmail(user.getName(),user.getEmail(),confirmation.getToken());
    return user;
  }

  @Override
  public Boolean verifyToken(String token) {
    Confirmation confirmation = confirmationRepository.findByToken(token);
    var user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
    user.setEnabled(true);
    userRepository.save(user);
    //confirmationRepository.delete(confirmation);
    return Boolean.TRUE;
  }
}
