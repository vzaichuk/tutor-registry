package ua.com.registry.tutor.authentication.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.authentication.domain.dto.RoleDto;
import ua.com.registry.tutor.authentication.domain.dto.UserSignupDto;
import ua.com.registry.tutor.authentication.domain.entity.User;
import ua.com.registry.tutor.authentication.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<User> signup(@Validated @RequestBody UserSignupDto dto) {
    return ResponseEntity.ok(userService.registerUser(dto));
  }

  @PostMapping("/role")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<User> setRole(@Validated @RequestBody RoleDto dto, Authentication auth) {
    return ResponseEntity.ok(userService.setRole(dto, auth));
  }

  @GetMapping
  public ResponseEntity<Object> getMe(Authentication authentication) {
    return ResponseEntity.ok(authentication);
  }
}
