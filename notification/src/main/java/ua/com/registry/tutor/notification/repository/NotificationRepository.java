package ua.com.registry.tutor.notification.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ua.com.registry.tutor.notification.domain.entity.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String> {

  @Query(value = "{userId: ?0}")
  Optional<Notification> findByUserId(int userId);
}
