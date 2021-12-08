package ua.com.registry.tutor.authentication.domain.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {

  private String username;
  private String password;
  private String passwordConfirmation;
}
