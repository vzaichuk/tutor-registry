package ua.com.registry.tutor.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiceUriProvider {

  @Value("${GATEWAY_HOST}")
  private String gatewayServiceName;

  @Value("${ACCOUNT_HOST}")
  private String accountServiceName;

  @Value("${REGISTRATION_HOST}")
  private String registrationServiceName;

  @Value("${AUTHENTICATION_HOST}")
  private String authenticationServiceName;

  @Value("${CLIENT_HOST}")
  private String clientServiceName;
  @Value("${CLIENT_PORT}")
  private String clientServicePort;

  @Value("${SERVICE_SCHEME:lb}")
  private String serviceScheme;

  public String getAuthenticationUri() {
    return serviceScheme + "://" + authenticationServiceName;
  }

  public String getAccountUri() {
    return serviceScheme + "://" + accountServiceName;
  }

  public String getRegistrationUri() {
    return serviceScheme + "://" + registrationServiceName;
  }

  public String getGatewayUri() {
    return serviceScheme + "://" + gatewayServiceName;
  }

  public String getClientUri() {
    return "http://" + clientServiceName + ":" + clientServicePort;
  }

  public String getRegistrationAssignUri() {
    return getGatewayUri() + "/registration/assign";
  }

  public String getRegistrationAssignedUri(int id) {
    return getGatewayUri() + "/registration/assigned/" + id;
  }
}
