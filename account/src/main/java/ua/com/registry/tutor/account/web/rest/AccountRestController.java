package ua.com.registry.tutor.account.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.account.domain.dto.AcceptRequestDto;
import ua.com.registry.tutor.account.domain.entity.Account;
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

  @GetMapping("/review")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAccounts(Pageable pageable) {
    return ResponseEntity.ok(accountService.getAccountsOnReview(pageable));
  }

  @GetMapping("/{id:\\d+}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Account> getOne(@PathVariable Integer id) {
    return ResponseEntity.ok(accountService.getAccountById(id));
  }

  @PostMapping("/assign/{tutorId}")
  @PreAuthorize("hasRole('STUDENT')")
  public ResponseEntity<Boolean> assignStudentToTutor(
      @PathVariable int tutorId, Authentication authentication,
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token
  ) {
    return ResponseEntity.ok(accountService.assignStudentToTutor(tutorId, authentication, token));
  }

  @PutMapping("/accept")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Account> accept(@RequestBody AcceptRequestDto dto) {
    return ResponseEntity.ok(accountService.accept(dto));
  }
}
