package ua.com.registry.tutor.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateNotificationDto {

  private int userId;
  private String content;
}
