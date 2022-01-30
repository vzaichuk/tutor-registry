package ua.com.registry.tutor.account.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.registry.tutor.account.domain.entity.Account;
import ua.com.registry.tutor.common.domain.dto.AssignmentRequestDto;
import ua.com.registry.tutor.common.service.ServiceUriProvider;

@Service
@RequiredArgsConstructor
public class RegistrationService {

  @LoadBalanced
  private final RestTemplate restTemplate;
  private final ServiceUriProvider serviceUriProvider;

  public Integer getTutorIdByStudentId(Integer studentId, String token) {
    HttpEntity<AssignmentRequestDto> request = new HttpEntity<>(null, getHeaders(token));

    return restTemplate.exchange(serviceUriProvider
        .getRegistrationAssignedUri(studentId), HttpMethod.GET, request, Integer.class)
        .getBody();
  }

  public List<Integer> getStudentIdsByTutorId(Integer tutorId, String token) {
    HttpEntity<AssignmentRequestDto> request = new HttpEntity<>(null, getHeaders(token));

    return (List<Integer>) restTemplate.exchange(serviceUriProvider
        .getRegistrationAssigneesUri(tutorId), HttpMethod.GET, request, List.class)
        .getBody();
  }

  public Boolean assign(Account tutorAccount, Account studentAccount, String token) {
    HttpEntity<AssignmentRequestDto> request = new HttpEntity<>(
        new AssignmentRequestDto(tutorAccount.getId(), studentAccount.getId()), getHeaders(token));

    return restTemplate
        .postForObject(serviceUriProvider.getRegistrationAssignUri(), request, Boolean.class);
  }

  private HttpHeaders getHeaders(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, token);
    return headers;
  }
}
