package ua.com.registry.tutor.authentication.service;

import java.io.IOException;

public interface Oauth2ProviderService {

  /**
   * Get URL to authorize user on auth provider side.
   * @return Authorization url.
   */
  String getAuthorizationUrl();

  /**
   * Get email address from user info on authorization server.
   *
   * @param code Authorization code granted by authorization server.
   * @return Email address of authorized user.
   */
  String getEmail(String code) throws IOException;
}
