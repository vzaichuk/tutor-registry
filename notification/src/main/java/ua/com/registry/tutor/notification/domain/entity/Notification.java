package ua.com.registry.tutor.notification.domain.entity;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

  @Id
  private String id;
  private int userId;
  private Map<String, Message> messages;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Message {

    private String id;
    private boolean seen;
    private String content;
    private LocalDateTime created;
  }
}
