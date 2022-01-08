package ua.com.registry.tutor.authentication.config;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import ua.com.registry.tutor.authentication.domain.entity.User;
import ua.com.registry.tutor.authentication.service.UserService;

@RequiredArgsConstructor
public class AuthenticationTokenEnhancer implements TokenEnhancer {

  private final UserService userService;

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    User user = userService.findByUserName(authentication.getUserAuthentication().getName())
        .orElseThrow(() -> new IllegalStateException("User not found"));

    Map<String, Object> additionalInfo = new HashMap<>();
    additionalInfo.put("user_id", user.getId());

    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    return accessToken;
  }
}
