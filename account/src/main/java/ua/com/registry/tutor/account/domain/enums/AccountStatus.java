package ua.com.registry.tutor.account.domain.enums;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountStatus {

  REVIEW(0),
  ACTIVE(1),
  REJECTED(2);

  private final int value;

  public static AccountStatus of(int value) {
    return Arrays.stream(AccountStatus.values())
        .filter(r -> r.getValue() == value).findAny()
        .orElseThrow(() -> new IllegalStateException("Account status is not supported"));
  }
}
