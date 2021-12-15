package ua.com.registry.tutor.authentication.web.rest;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.authentication.domain.dto.UserSignupDto;
import ua.com.registry.tutor.authentication.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Valid @RequestBody UserSignupDto dto) {
    userService.registerUser(dto);
    return ResponseEntity.noContent().build();
  }
}
