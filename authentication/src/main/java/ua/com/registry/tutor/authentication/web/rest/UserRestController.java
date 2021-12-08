package ua.com.registry.tutor.authentication.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.authentication.domain.dto.UserRegistrationDto;
import ua.com.registry.tutor.authentication.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<?> registerUser(@Validated @RequestBody UserRegistrationDto dto) {
    userService.registerUser(dto);
    return ResponseEntity.noContent().build();
  }
}
