package ua.com.registry.tutor.account.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ua.com.registry.tutor.account.domain.entity.Account;
import ua.com.registry.tutor.account.domain.enums.AccountStatus;
import ua.com.registry.tutor.account.domain.repository.AccountRepository;
import ua.com.registry.tutor.common.domain.enums.UserRole;
import ua.com.registry.tutor.common.service.AuthenticationHelper;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final AuthenticationHelper authenticationHelper;

  public Account getAccountFromAuthentication(Authentication authentication) {
    int userId = authenticationHelper.getUserId(authentication);

    Optional<Account> accountOptional = accountRepository.findOneByUserId(userId);

    if (accountOptional.isPresent()) {
      return accountOptional.get();
    }

    if (Objects.nonNull(authentication.getAuthorities())
        && !authentication.getAuthorities().isEmpty()) {
      return create(userId, authentication.getAuthorities());
    }

    Account account = new Account();
    account.setUserId(0);
    account.setUsername("");
    account.setBio("");
    account.setRole(null);
    account.setStatus(AccountStatus.REVIEW.getValue());

    return account;
  }

  public Account create(
      int userId, Collection<? extends GrantedAuthority> authorities
  ) {
    GrantedAuthority authority = authorities.iterator().next();
    Account account = new Account();
    account.setUserId(userId);
    account.setUsername("");
    account.setBio("");
    account.setRole(UserRole.of(authority.getAuthority()));
    account.setStatus(AccountStatus.REVIEW.getValue());

    account = accountRepository.save(account);

    // TODO: Send notification about new account creation.

    return account;
  }
}
