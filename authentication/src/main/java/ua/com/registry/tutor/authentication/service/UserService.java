package ua.com.registry.tutor.authentication.service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.registry.tutor.authentication.domain.dto.RoleDto;
import ua.com.registry.tutor.authentication.domain.dto.UserSignupDto;
import ua.com.registry.tutor.authentication.domain.entity.User;
import ua.com.registry.tutor.authentication.domain.repository.UserRepository;
import ua.com.registry.tutor.common.domain.enums.UserRole;
import ua.com.registry.tutor.common.service.AuthenticationHelper;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final AuthenticationHelper authenticationHelper;

  private final Set<UserRole> allowedRoles = Set.of(UserRole.STUDENT, UserRole.TUTOR);

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public User registerUser(UserSignupDto dto) {
    userRepository.findByUsername(dto.getEmail())
        .ifPresent(u -> { throw new IllegalStateException("User already exists"); });

    return createUser(dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public User setRole(RoleDto dto, Authentication authentication) {
    if (!authentication.getAuthorities().isEmpty()) {
      throw new IllegalStateException("Role has been already assigned");
    }

    UserRole role = UserRole.of(dto.getRole());
    if (!allowedRoles.contains(role)) {
      throw new IllegalArgumentException("Selected role is not allowed");
    }

    User user = userRepository.findById(authenticationHelper.getUserId(authentication))
        .orElseThrow(() -> new NoSuchElementException("User not found"));

    user.setRoles(role);

    return userRepository.save(user);
  }

  public Optional<User> findByUserName(String username) {
    return userRepository.findByUsername(Objects.requireNonNull(username).trim().toLowerCase());
  }

  public User createUser(String email, String password) {
    User user = new User();
    user.setUsername(email.trim().toLowerCase());
    user.setPassword(password);
    user.setEnabled(true);

    return userRepository.save(user);
  }
}
