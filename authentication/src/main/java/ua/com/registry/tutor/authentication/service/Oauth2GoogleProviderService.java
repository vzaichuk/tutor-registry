package ua.com.registry.tutor.authentication.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2;
import java.io.IOException;
import java.util.Collections;
import ua.com.registry.tutor.authentication.config.Oauth2Properties;
import ua.com.registry.tutor.authentication.domain.enums.GrantType;

public class Oauth2GoogleProviderService implements Oauth2ProviderService {

  private static final String SCOPE_URL = "https://www.googleapis.com/auth/userinfo.email";

  private final GoogleAuthorizationCodeFlow codeFlow;
  private final Oauth2Properties properties;

  public Oauth2GoogleProviderService(Oauth2Properties oauth2Properties) {
    properties = oauth2Properties;
    codeFlow = new GoogleAuthorizationCodeFlow.Builder(
        new NetHttpTransport(),
        GsonFactory.getDefaultInstance(),
        oauth2Properties.getGoogle().getClientId(),
        oauth2Properties.getGoogle().getClientSecret(),
        Collections.singletonList(SCOPE_URL))
        .build();
  }

  @Override
  public String getAuthorizationUrl() {
    return codeFlow.newAuthorizationUrl()
        .setRedirectUri(properties.getGoogle().getRedirectUrl())
        .build();
  }

  @Override
  public String getEmail(String code) throws IOException {
    GoogleTokenResponse response = codeFlow.newTokenRequest(code)
        .setRedirectUri(properties.getGoogle().getRedirectUrl())
        .setGrantType(GrantType.AUTHORIZATION_CODE.getValue())
        .execute();

    GoogleCredential credential = new GoogleCredential().setAccessToken(response.getAccessToken());
    Oauth2 oauth2 = new Oauth2
        .Builder(new NetHttpTransport(), new GsonFactory(), credential)
        .build();

    return oauth2.userinfo().get().execute().getEmail();
  }
}
