package ua.com.registry.tutor.account.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<Page<Account>> getMyTutors(Pageable pageable) {
    return ResponseEntity.ok(accountService.getAllTutors(pageable));
  }
}

