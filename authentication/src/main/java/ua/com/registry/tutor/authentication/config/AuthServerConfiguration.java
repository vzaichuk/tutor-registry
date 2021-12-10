package ua.com.registry.tutor.authentication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import ua.com.registry.tutor.authentication.config.security.CustomClientDetailsService;
import ua.com.registry.tutor.authentication.service.AuthClientService;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;
  private final AuthClientService authClientService;
  private final UserDetailsService userDetailsService;

  @Value("${jwt.key}")
  private String jwtKey;

  @Override
  public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
    configurer.withClientDetails(new CustomClientDetailsService(authClientService));
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer configurer) {
    configurer.authenticationManager(authenticationManager)
        .tokenStore(tokenStore())
        .userDetailsService(userDetailsService)
        .accessTokenConverter(jwtAccessTokenConverter());
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(jwtKey);
    return converter;
  }
}
