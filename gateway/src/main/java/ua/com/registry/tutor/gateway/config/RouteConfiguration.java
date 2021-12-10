package ua.com.registry.tutor.gateway.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import ua.com.registry.tutor.gateway.filter.BodyToParamsGatewayFilterFactory;
import ua.com.registry.tutor.gateway.filter.BodyToParamsGatewayFilterFactory.Config;

@Configuration
@RequiredArgsConstructor
public class RouteConfiguration {

  public static final String SCOPE = "scope";
  public static final String GRANT_TYPE = "grant_type";
  public static final String USERNAME = "username";
  public static final String PASSWORD = "password";
  public static final String REFRESH_TOKEN = "refresh_token";
  public static final String SCOPE_READ = "read";

  private final BodyToParamsGatewayFilterFactory bodyToParamsGatewayFilterFactory;

  @Value("${ACCOUNT_HOST}")
  private String accountServiceName;
  @Value("${ACCOUNT_PORT}")
  private String accountServicePort;

  @Value("${AUTHENTICATION_HOST}")
  private String authorizationServiceName;
  @Value("${AUTHENTICATION_PORT}")
  private String authorizationServicePort;

//  @Value("${GATEWAY_CLIENT_SECRET}") // TODO: Uncomment and add env var.
  private String gatewayClientSecret = "Basic Z2F0ZXdheTpzZWNyZXQ=";

  @Bean
  RouteLocator routeLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("authorization_via_credentials",
            route -> route.path("/authorization/login/credentials")
                .and().method(HttpMethod.POST)
                .and().readBody(String.class, StringUtils::hasText)
                .filters(filter -> filter.stripPrefix(1)
                    .setPath("/oauth/token")
                    .setRequestHeader(HttpHeaders.AUTHORIZATION, gatewayClientSecret)
                    .addRequestParameter(GRANT_TYPE, PASSWORD)
                    .addRequestParameter(SCOPE, SCOPE_READ)
                    .filter(bodyToParamsGatewayFilterFactory.apply(new Config(new HashSet<>(
                        Arrays.asList(USERNAME, PASSWORD))))))
                .uri(getAuthorizationServiceUri())
        )
        .route("authorization_via_refresh_token",
            route -> route.path("/authorization/login/refresh")
                .and().method(HttpMethod.POST)
                .and().readBody(String.class, StringUtils::hasText)
                .filters(filter -> filter.stripPrefix(1)
                    .setPath("/oauth/token")
                    .setRequestHeader(HttpHeaders.AUTHORIZATION, gatewayClientSecret)
                    .addRequestParameter(GRANT_TYPE, REFRESH_TOKEN)
                    .addRequestParameter(SCOPE, SCOPE_READ)
                    .filter(bodyToParamsGatewayFilterFactory.apply(new Config(new HashSet<>(
                        Collections.singletonList(REFRESH_TOKEN))))))
                .uri(getAuthorizationServiceUri())
        )
        .route("account_get",
            route -> route
                .path("/account/**")
                .and().method(HttpMethod.GET)
                .filters(filter -> filter.stripPrefix(1))
                .uri(getAccountServiceUri()))
        .build();
  }

  private String getAuthorizationServiceUri() {
    return "http://" + authorizationServiceName + ":" + authorizationServicePort;
  }

  private String getAccountServiceUri() {
    return "http://" + accountServiceName + ":" + accountServicePort;
  }
}
