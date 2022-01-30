package ua.com.registry.tutor.account.web.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.account.domain.entity.Account;
import ua.com.registry.tutor.account.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentRestController {

  private final AccountService accountService;

  @GetMapping("/my")
  @PreAuthorize("hasRole('TUTOR')")
  public ResponseEntity<List<Account>> getMyStudents(
      Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String token
  ) {
    return ResponseEntity.ok(accountService.getMyStudents(authentication, token));
  }
}
