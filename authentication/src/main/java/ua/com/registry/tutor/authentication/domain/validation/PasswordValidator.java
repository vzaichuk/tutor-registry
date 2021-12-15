package ua.com.registry.tutor.authentication.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class PasswordValidator implements ConstraintValidator<Password, String> {

  @Override
  public void initialize(Password password) {
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext cxt) {
    if (!StringUtils.hasText(password) || password.length() < 8) {
      return false;
    }

    if (!password.matches(".*[0-9]+.*")) {
      return false;
    }

    if (!password.matches(".*[A-Z]+.*")) {
      return false;
    }

    return password.matches(".*[a-z]+.*");
  }
}
