package ua.com.registry.tutor.common.domain.enums;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

  STUDENT("ROLE_STUDENT"),
  TUTOR("ROLE_TUTOR"),
  ADMIN("ROLE_ADMIN");

  private final String value;

  public static UserRole of(String value) {
    return Arrays.stream(UserRole.values())
        .filter(r -> r.getValue().equals(value)).findAny()
        .orElseThrow(() -> new IllegalStateException("User role is not supported"));
  }
}
