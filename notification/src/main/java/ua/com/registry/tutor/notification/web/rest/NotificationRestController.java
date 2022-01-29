package ua.com.registry.tutor.notification.web.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.com.registry.tutor.common.domain.dto.CreateNotificationDto;
import ua.com.registry.tutor.common.service.AuthenticationHelper;
import ua.com.registry.tutor.notification.domain.entity.Notification.Message;
import ua.com.registry.tutor.notification.service.NotificationService;

@RestController
@RequiredArgsConstructor
public class NotificationRestController {

  private final NotificationService notificationService;
  private final AuthenticationHelper authenticationHelper;

  @GetMapping("/all")
  public ResponseEntity<List<Message>> getAll(Authentication authentication) {
    return ResponseEntity
        .ok(notificationService.getAll(authenticationHelper.getUserId(authentication)));
  }

  @GetMapping("/all/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<Message>> getAllByUserId(@PathVariable int id) {
    return ResponseEntity.ok(notificationService.getAll(id));
  }

  @GetMapping("/unseen-amount")
  public ResponseEntity<Long> getUnseenAmount(Authentication authentication) {
    return ResponseEntity
        .ok(notificationService.getUnseenAmount(authenticationHelper.getUserId(authentication)));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/unseen-amount/{userId}")
  public ResponseEntity<Long> getUnseenAmount(@PathVariable int userId) {
    return ResponseEntity.ok(notificationService.getUnseenAmount(userId));
  }

  @PostMapping("/add")
  public ResponseEntity<Boolean> add(@RequestBody CreateNotificationDto dto) {
    return ResponseEntity.ok(notificationService.add(dto));
  }

  @PutMapping("/seen/{id}")
  public ResponseEntity<Boolean> seen(@PathVariable String id, Authentication authentication) {
    return ResponseEntity.ok(
        notificationService.seen(authenticationHelper.getUserId(authentication), id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/seen/{userId}/{id}")
  public ResponseEntity<Boolean> seen(@PathVariable int userId, @PathVariable String id) {
    return ResponseEntity.ok(notificationService.seen(userId, id));
  }

  @DeleteMapping("/remove/{id}")
  public ResponseEntity<Boolean> remove(@PathVariable String id, Authentication authentication) {
    return ResponseEntity.ok(
        notificationService.remove(authenticationHelper.getUserId(authentication), id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/remove/{userId}/{id}")
  public ResponseEntity<Boolean> removeByUserId(@PathVariable int userId, @PathVariable String id) {
    return ResponseEntity.ok(notificationService.remove(userId, id));
  }

  @DeleteMapping("/remove-all")
  public ResponseEntity<Boolean> removeAllSeen(Authentication authentication) {
    return ResponseEntity.ok(
        notificationService.removeAllSeen(authenticationHelper.getUserId(authentication)));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/remove-all/{userId}")
  public ResponseEntity<Boolean> removeAllSeen(@PathVariable int userId) {
    return ResponseEntity.ok(notificationService.removeAllSeen(userId));
  }
}
