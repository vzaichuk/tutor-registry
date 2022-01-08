package ua.com.registry.tutor.common.service;

import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {

  public int getUserId(Authentication authentication) {
    if (!(authentication instanceof OAuth2Authentication)) {
      throw new IllegalStateException("Authentication isn't OAuth2");
    }

    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
    if (!(details.getDecodedDetails() instanceof Map)) {
      throw new IllegalStateException("Invalid decoded details");
    }

    Map<String, Object> decodedDetails = (Map<String, Object>) details.getDecodedDetails();
    if (!decodedDetails.containsKey(getIdKey())) {
      throw new IllegalStateException("User identifier is absent");
    }

    return (Integer) decodedDetails.get(getIdKey());
  }

  public String getIdKey() {
    return "user_id";
  }
}
