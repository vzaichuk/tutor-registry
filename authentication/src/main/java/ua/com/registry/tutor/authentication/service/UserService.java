package ua.com.registry.tutor.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.registry.tutor.authentication.domain.dto.UserRegistrationDto;
import ua.com.registry.tutor.authentication.domain.entity.User;
import ua.com.registry.tutor.authentication.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void registerUser(UserRegistrationDto dto) {
    userRepository.findByUsername(dto.getUsername())
        .ifPresent(u -> { throw new IllegalStateException("User already exists"); });

    User user = new User();
    user.setUsername(dto.getUsername().trim().toLowerCase());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setEnabled(true);

    userRepository.save(user);
  }
}
