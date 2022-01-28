package ua.com.registry.tutor.notification.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.registry.tutor.common.domain.dto.CreateNotificationDto;
import ua.com.registry.tutor.notification.domain.entity.Notification;
import ua.com.registry.tutor.notification.domain.entity.Notification.Message;
import ua.com.registry.tutor.notification.repository.NotificationRepository;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;

  public boolean add(CreateNotificationDto dto) {
    Notification notification = getByUserId(dto.getUserId());

    String id = UUID.randomUUID().toString();
    notification.getMessages()
        .put(id, new Message(id, false, dto.getContent(), LocalDateTime.now()));

    notificationRepository.save(notification);

    return true;
  }

  public boolean seen(int userId, String id) {
    Notification notification = getByUserId(userId);
    notification.getMessages().computeIfPresent(id, (i, message) -> {
      message.setSeen(true);
      return message;
    });
    notificationRepository.save(notification);
    return true;
  }

  public boolean remove(int userId, String id) {
    Notification notification = getByUserId(userId);
    notification.getMessages().remove(id);
    notificationRepository.save(notification);
    return true;
  }

  public boolean removeAllSeen(int userId) {
    Notification notification = getByUserId(userId);
    notification.getMessages().values().removeIf(Message::isSeen);
    notificationRepository.save(notification);
    return true;
  }

  private Notification getByUserId(int userId) {
    return notificationRepository.findByUserId(userId)
        .orElseGet(() -> new Notification(null, userId, new HashMap<>()));
  }
}
