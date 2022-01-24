package ua.com.registry.tutor.account.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;
import ua.com.registry.tutor.common.config.AdvancedAccessTokenConverter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  private final RestTemplateBuilder restTemplateBuilder;

  @Value("${jwt.key}")
  private String jwtKey;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.tokenStore(tokenStore());
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    var converter = new JwtAccessTokenConverter();
    converter.setAccessTokenConverter(new AdvancedAccessTokenConverter());
    converter.setSigningKey(jwtKey);
    return converter;
  }

  @Bean
  public RestTemplate restTemplate() {
    return restTemplateBuilder
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}
