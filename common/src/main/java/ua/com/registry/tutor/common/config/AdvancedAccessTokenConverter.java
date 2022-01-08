package ua.com.registry.tutor.common.config;

import java.util.Map;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

public class AdvancedAccessTokenConverter extends DefaultAccessTokenConverter {

  @Override
  public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
    OAuth2Authentication authentication = super.extractAuthentication(claims);
    authentication.setDetails(claims);
    return authentication;
  }
}
