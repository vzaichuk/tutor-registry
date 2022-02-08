package ua.com.registry.tutor.authentication.web.rest;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.authentication.domain.entity.User;
import ua.com.registry.tutor.authentication.service.JwtAccessTokenCreatorService;
import ua.com.registry.tutor.authentication.service.Oauth2ProviderFactoryService;
import ua.com.registry.tutor.authentication.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class Oauth2RestController {

  private final UserService userService;
  private final Oauth2ProviderFactoryService oauth2ProviderFactoryService;
  private final JwtAccessTokenCreatorService jwtAccessTokenCreatorService;

  @GetMapping("/authorization/{id}")
  public ResponseEntity<String> getAuthorizationUrl(@PathVariable String id) {
    return ResponseEntity.ok(oauth2ProviderFactoryService.build(id).getAuthorizationUrl());
  }

  @PostMapping("/code/{id}")
  public ResponseEntity<OAuth2AccessToken> authenticate(
      @PathVariable String id, @RequestParam String code
  ) throws IOException {
    String email = oauth2ProviderFactoryService.build(id).getEmail(code);
    String password = "none";

    User user = userService.findByUserName(email)
        .orElseGet(() -> userService.createUser(email, password));

    return ResponseEntity.ok(jwtAccessTokenCreatorService.create(user));
  }
}
