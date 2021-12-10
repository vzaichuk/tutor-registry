package ua.com.registry.tutor.account.web.rest;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AccountRestController {

  @GetMapping("/me")
  public ResponseEntity<?> me() {
    Map<String, Object> response = new HashMap<>();
    Map<String, String> user = new HashMap<>();
    user.put("id", "22");
    user.put("name", "Username");
    user.put("role", "student");
    response.put("user", user);
    return ResponseEntity.ok(response);
  }
}
