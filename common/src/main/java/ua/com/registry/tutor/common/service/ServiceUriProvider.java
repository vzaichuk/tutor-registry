package ua.com.registry.tutor.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiceUriProvider {

  public static final String AUTHENTICATION_PATH = "/authentication";
  public static final String ACCOUNT_PATH = "/account";
  public static final String REGISTRATION_PATH = "/registration";
  public static final String NOTIFICATION_PATH = "/notification";

  @Value("${GATEWAY_HOST}")
  private String gatewayServiceName;

  @Value("${ACCOUNT_HOST}")
  private String accountServiceName;

  @Value("${REGISTRATION_HOST}")
  private String registrationServiceName;

  @Value("${NOTIFICATION_HOST}")
  private String notificationServiceName;

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

  public String getNotificationUri() {
    return serviceScheme + "://" + notificationServiceName;
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

  public String getRegistrationAssigneesUri(int id) {
    return getGatewayUri() + "/registration/assignees/" + id;
  }

  public String getNotificationCreateUrl() {
    return getGatewayUri() + NOTIFICATION_PATH + "/add";
  }

  public String getNotificationSeenUrl(String id) {
    return getGatewayUri() + NOTIFICATION_PATH + "/seen/" + id;
  }

  public String getNotificationRemoveUrl(String id) {
    return getGatewayUri() + NOTIFICATION_PATH + "/remove/" + id;
  }

  public String getNotificationRemoveAllUrl() {
    return getGatewayUri() + NOTIFICATION_PATH + "/remove-all";
  }
}
