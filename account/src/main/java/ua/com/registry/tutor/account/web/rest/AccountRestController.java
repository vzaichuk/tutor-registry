package ua.com.registry.tutor.account.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountRestController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello";
  }
}
