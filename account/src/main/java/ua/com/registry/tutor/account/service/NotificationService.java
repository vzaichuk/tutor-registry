package ua.com.registry.tutor.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.registry.tutor.common.domain.dto.CreateNotificationDto;
import ua.com.registry.tutor.common.service.ServiceUriProvider;

@Service
@RequiredArgsConstructor
public class NotificationService {

  @LoadBalanced
  private final RestTemplate restTemplate;
  private final ServiceUriProvider serviceUriProvider;

  public Boolean add(int userId, String content, String token) {
    HttpEntity<CreateNotificationDto> request =
        new HttpEntity<>(new CreateNotificationDto(userId, content), getHeaders(token));

    Boolean created = restTemplate.postForObject(serviceUriProvider
        .getNotificationCreateUrl(), request, Boolean.class);

    return Boolean.TRUE.equals(created);
  }

  public Boolean seen(String messageId, String token) {
    HttpEntity<?> request = new HttpEntity<>(null, getHeaders(token));

    Boolean created = restTemplate.postForObject(serviceUriProvider
        .getNotificationSeenUrl(messageId), request, Boolean.class);

    return Boolean.TRUE.equals(created);
  }

  public Boolean remove(String messageId, String token) {
    HttpEntity<?> request = new HttpEntity<>(null, getHeaders(token));

    Boolean created = restTemplate.postForObject(serviceUriProvider
        .getNotificationRemoveUrl(messageId), request, Boolean.class);

    return Boolean.TRUE.equals(created);
  }

  public Boolean removeAllSeen(String token) {
    HttpEntity<?> request = new HttpEntity<>(null, getHeaders(token));

    ResponseEntity<Boolean> created = restTemplate.exchange(serviceUriProvider
        .getNotificationRemoveAllUrl(), HttpMethod.DELETE, request, Boolean.class);

    return Boolean.TRUE.equals(created.getBody());
  }

  private HttpHeaders getHeaders(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, token);
    return headers;
  }
}
