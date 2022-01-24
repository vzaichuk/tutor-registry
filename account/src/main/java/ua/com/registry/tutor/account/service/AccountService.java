package ua.com.registry.tutor.account.service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import ua.com.registry.tutor.account.domain.dto.AcceptRequestDto;
import ua.com.registry.tutor.account.domain.entity.Account;
import ua.com.registry.tutor.account.domain.enums.AccountStatus;
import ua.com.registry.tutor.account.domain.repository.AccountRepository;
import ua.com.registry.tutor.common.domain.dto.AssignmentRequestDto;
import ua.com.registry.tutor.common.domain.enums.UserRole;
import ua.com.registry.tutor.common.service.AuthenticationHelper;
import ua.com.registry.tutor.common.service.ServiceUriProvider;

@Service
@RequiredArgsConstructor
public class AccountService {

  @LoadBalanced
  private final RestTemplate restTemplate;
  private final AccountRepository accountRepository;
  private final AuthenticationHelper authenticationHelper;
  private final ServiceUriProvider serviceUriProvider;
  private final Account EMPTY_ACCOUNT = new Account();

  @PostConstruct
  public void init() {
    EMPTY_ACCOUNT.setUserId(0);
    EMPTY_ACCOUNT.setUsername("");
    EMPTY_ACCOUNT.setBio("");
    EMPTY_ACCOUNT.setRole(null);
    EMPTY_ACCOUNT.setStatus(AccountStatus.REVIEW.getValue());
  }

  public Account getAccountFromAuthentication(Authentication authentication) {
    int userId = authenticationHelper.getUserId(authentication);

    Optional<Account> accountOptional = accountRepository.findOneByUserId(userId);

    if (accountOptional.isPresent()) {
      return accountOptional.get();
    }

    if (Objects.nonNull(authentication.getAuthorities())
        && !authentication.getAuthorities().isEmpty()) {
      boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(a -> UserRole.ADMIN.getValue().equals(a.getAuthority()));
      if (isAdmin) {
        return create(userId, UserRole.ADMIN);
      }

      Account account = persist(create(
          userId, UserRole.of(authentication.getAuthorities().iterator().next().getAuthority())));

      // TODO: Create notification for admin.

      return account;
    }

    return EMPTY_ACCOUNT;
  }

  public Page<Account> getAccountsOnReview(Pageable pageable) {
    return accountRepository.findAllByStatusOrderById(AccountStatus.REVIEW.getValue(), pageable);
  }

  public Account getAccountById(Integer id) {
    return accountRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Account not found"));
  }

  public Account accept(AcceptRequestDto dto) {
    Account account = accountRepository.findById(dto.getId())
        .orElseThrow(() -> new NoSuchElementException("Account not found"));

    if (AccountStatus.of(account.getStatus()) != AccountStatus.REVIEW) {
      throw new IllegalStateException("Decision already has been applied for this account");
    }

    account.setStatus(
        dto.isDecision() ? AccountStatus.ACTIVE.getValue() : AccountStatus.REJECTED.getValue());

    account = persist(account);

    // TODO: Create notification for user about making a decision about his application.

    return account;
  }

  public Page<Account> getAllTutors(Pageable pageable) {
    return accountRepository.findAllByRoleAndStatus(
        UserRole.TUTOR.getValue(), AccountStatus.ACTIVE.getValue(), pageable);
  }

  public Boolean assignStudentToTutor(
      int tutorAccountId, Authentication authentication, String token
  ) {
    Account studentAccount = getAccountFromAuthentication(authentication);
    Account tutorAccount = getAccountById(tutorAccountId);

    // Send request.
    if (!StringUtils.hasText(token)) {
      throw new IllegalStateException("Authorization token is absent");
    }

    HttpEntity<AssignmentRequestDto> request = new HttpEntity<>(
        new AssignmentRequestDto(tutorAccount.getId(), studentAccount.getId()));
    request.getHeaders().add(HttpHeaders.AUTHORIZATION, token);

    return restTemplate
        .postForObject(serviceUriProvider.getRegistrationAssignUri(), request, Boolean.class);
  }

  private Account create(int userId, UserRole role) {
    Account account = new Account();
    account.setUserId(userId);
    account.setUsername("user-" + userId);
    account.setBio("");
    account.setRole(role);
    account.setStatus(AccountStatus.REVIEW.getValue());

    return account;
  }

  private Account persist(Account account) {
    return accountRepository.save(account);
  }
}
