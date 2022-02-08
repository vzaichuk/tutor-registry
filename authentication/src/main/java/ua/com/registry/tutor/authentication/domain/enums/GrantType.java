package ua.com.registry.tutor.authentication.domain.enums;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GrantType {

  PASSWORD("password"),
  REFRESH_TOKEN("refresh_token"),
  AUTHORIZATION_CODE("authorization_code");

  private final String value;

  public static GrantType of(String value) {
    return Arrays.stream(GrantType.values())
        .filter(t -> t.value.equals(value)).findAny()
        .orElseThrow(IllegalStateException::new);
  }
}
