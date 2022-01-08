package ua.com.registry.tutor.account.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.com.registry.tutor.account.domain.entity.Account;

@Data
@AllArgsConstructor
public class AccountWrapperDto {

  private Account user;
}
