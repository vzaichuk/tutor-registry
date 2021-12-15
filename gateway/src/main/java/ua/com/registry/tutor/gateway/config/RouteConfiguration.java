package ua.com.registry.tutor.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import ua.com.registry.tutor.gateway.filter.AuthorizationToParamsGatewayFilterFactory;
import ua.com.registry.tutor.gateway.filter.AuthorizationToParamsGatewayFilterFactory.Config;

@Configuration
@RequiredArgsConstructor
public class RouteConfiguration {

  public static final String OAUTH_URL = "/oauth/token";
  public static final String SCOPE = "scope";
  public static final String GRANT_TYPE = "grant_type";
  public static final String PASSWORD = "password";
  public static final String REFRESH_TOKEN = "refresh_token";
  public static final String SCOPE_READ = "read";

  private final AuthorizationToParamsGatewayFilterFactory authorizationToParamsGatewayFilterFactory;

  @Value("${ACCOUNT_HOST}")
  private String accountServiceName;
  @Value("${ACCOUNT_PORT}")
  private String accountServicePort;

  @Value("${AUTHENTICATION_HOST}")
  private String authorizationServiceName;
  @Value("${AUTHENTICATION_PORT}")
  private String authorizationServicePort;

  @Value("${CLIENT_HOST}")
  private String clientServiceName;
  @Value("${CLIENT_PORT}")
  private String clientServicePort;

  @Value("${GATEWAY_CLIENT_SECRET}")
  private String gatewayClientSecret;
  @Value("${SERVICE_SCHEME:lb}")
  private String serviceScheme;

  @Bean
  RouteLocator routeLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("authentication_via_credentials",
            route -> route.path("/authentication/login/credentials")
                .and().method(HttpMethod.POST)
                .filters(filter -> filter.stripPrefix(1)
                    .setPath(OAUTH_URL)
                    .addRequestParameter(GRANT_TYPE, PASSWORD)
                    .addRequestParameter(SCOPE, SCOPE_READ)
                    .filter(authorizationToParamsGatewayFilterFactory.apply(new Config()))
                    .setRequestHeader(HttpHeaders.AUTHORIZATION, gatewayClientSecret))
                .uri(getAuthorizationServiceUri())
        )
        .route("authentication_via_refresh_token",
            route -> route.path("/authentication/login/refresh")
                .and().method(HttpMethod.POST)
                .filters(filter -> filter.stripPrefix(1)
                    .setPath(OAUTH_URL)
                    .addRequestParameter(GRANT_TYPE, REFRESH_TOKEN)
                    .addRequestParameter(SCOPE, SCOPE_READ)
                    .filter(authorizationToParamsGatewayFilterFactory.apply(new Config()))
                    .setRequestHeader(HttpHeaders.AUTHORIZATION, gatewayClientSecret))
                .uri(getAuthorizationServiceUri())
        )
        .route("authentication_service_requests",
            route -> route.path("/authentication/**")
                .filters(filter -> filter.stripPrefix(1))
                .uri(getAuthorizationServiceUri())
        )
        .route("account_get",
            route -> route
                .path("/account/**")
                .and().method(HttpMethod.GET)
                .filters(filter -> filter.stripPrefix(1))
                .uri(getAccountServiceUri()))
        .route("client_get",
            route -> route
                .path("/", "/client/**")
                .and().method(HttpMethod.GET)
                .filters(filter -> filter.stripPrefix(1))
                .uri(getClientServiceUri()))
        .route("img_get",
            route -> route
                .path("/img/**")
                .and().method(HttpMethod.GET)
                .uri(getClientServiceUri()))
        .build();
  }

  private String getAuthorizationServiceUri() {
    return serviceScheme + "://" + authorizationServiceName;
  }

  private String getAccountServiceUri() {
    return serviceScheme + "://" + accountServiceName;
  }

  private String getClientServiceUri() {
    return "http://" + clientServiceName + ":" + clientServicePort;
  }
}
