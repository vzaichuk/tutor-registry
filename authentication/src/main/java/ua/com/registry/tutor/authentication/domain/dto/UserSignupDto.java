package ua.com.registry.tutor.authentication.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import ua.com.registry.tutor.authentication.domain.validation.Password;
import ua.com.registry.tutor.authentication.domain.validation.ValueMatch;

@Data
@ValueMatch.List({
    @ValueMatch(
        property = "password",
        propertyToMatch = "passwordRepeat",
        message = "Passwords aren't equal")
})
public class UserSignupDto {

  @Email
  @NotEmpty
  private String email;

  @NotEmpty
  @Password
  private String password;
  private String passwordRepeat;
}
