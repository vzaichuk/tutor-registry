package ua.com.registry.tutor.account.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.account.service.AccountService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountRestController {

  private final AccountService accountService;

  @GetMapping
  public ResponseEntity<?> getAccount(Authentication authentication) {
    return ResponseEntity.ok(accountService.getAccountFromAuthentication(authentication));
  }
}
