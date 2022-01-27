package ua.com.registry.tutor.notification.domain.dto;

import lombok.Data;

@Data
public class CreateNotificationDto {

  private int userId;
  private String content;
}
