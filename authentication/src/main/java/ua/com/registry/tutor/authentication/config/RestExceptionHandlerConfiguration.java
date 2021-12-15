package ua.com.registry.tutor.authentication.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.com.registry.tutor.authentication.web.ErrorsResponse;

@RestControllerAdvice("ua.com.registry.tutor.authentication.web.rest")
public class RestExceptionHandlerConfiguration extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleBindException(
      BindException exception, HttpHeaders headers, HttpStatus status, WebRequest request
  ) {
    Map<String, String> errors = new HashMap<>();
    AtomicReference<String> defaultMessage = new AtomicReference<>("Validation failed");
    exception.getBindingResult().getAllErrors().forEach((error) -> {
      if (error instanceof FieldError) {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
      } else {
        defaultMessage.set(error.getDefaultMessage());
      }
    });

    ErrorsResponse errorsResponse = new ErrorsResponse(defaultMessage.get(), errors);
    return new ResponseEntity<>(errorsResponse, status);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request
  ) {
    return handleBindException(ex, headers, status, request);
  }
}
