package ua.com.registry.tutor.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.common.service.AuthenticationHelper;
import ua.com.registry.tutor.notification.domain.dto.CreateNotificationDto;
import ua.com.registry.tutor.notification.service.NotificationService;

@RestController
@RequiredArgsConstructor
public class NotificationRestController {

  private final NotificationService notificationService;
  private final AuthenticationHelper authenticationHelper;

  @PostMapping("/add")
  public ResponseEntity<Boolean> add(@RequestBody CreateNotificationDto dto) {
    return ResponseEntity.ok(notificationService.add(dto));
  }

  @PutMapping("/seen/{id}")
  public ResponseEntity<Boolean> seen(@PathVariable String id, Authentication authentication) {
    return ResponseEntity.ok(
        notificationService.seen(authenticationHelper.getUserId(authentication), id));
  }

  @DeleteMapping("/remove/{id}")
  public ResponseEntity<Boolean> remove(@PathVariable String id, Authentication authentication) {
    return ResponseEntity.ok(
        notificationService.remove(authenticationHelper.getUserId(authentication), id));
  }

  @DeleteMapping("/remove-all")
  public ResponseEntity<Boolean> removeAllSeen(Authentication authentication) {
    return ResponseEntity.ok(
        notificationService.removeAllSeen(authenticationHelper.getUserId(authentication)));
  }
}
