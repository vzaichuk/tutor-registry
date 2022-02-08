package ua.com.registry.tutor.authentication.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@NoArgsConstructor
@ConfigurationProperties("spring.security.oauth2.client.registration")
public class Oauth2Properties {

  private Source google;

  @Getter
  @Setter
  @NoArgsConstructor
  public static class Source {

    private String clientId;
    private String clientSecret;
    private String redirectUrl;
  }
}
