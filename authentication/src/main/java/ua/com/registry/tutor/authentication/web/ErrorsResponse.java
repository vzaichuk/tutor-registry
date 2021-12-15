package ua.com.registry.tutor.authentication.web;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorsResponse {

  private String message;
  private Map<String, String> errors;
}
