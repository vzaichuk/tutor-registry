package ua.com.registry.tutor.account.web.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.account.domain.entity.Account;
import ua.com.registry.tutor.account.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tutor")
public class TutorRestController {

  private final AccountService accountService;

  @GetMapping("/all")
  public ResponseEntity<Page<Account>> getAllTutors(Pageable pageable) {
    return ResponseEntity.ok(accountService.getAllTutors(pageable));
  }

  @GetMapping("/my")
  public ResponseEntity<List<Account>> getMyTutors(
      Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String token
  ) {
    return ResponseEntity.ok(accountService.getMyTutors(authentication, token));
  }
}

