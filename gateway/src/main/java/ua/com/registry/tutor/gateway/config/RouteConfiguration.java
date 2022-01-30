package ua.com.registry.tutor.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import ua.com.registry.tutor.common.service.ServiceUriProvider;
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
  private final ServiceUriProvider serviceUriProvider;

  @Value("${GATEWAY_CLIENT_SECRET}")
  private String gatewayClientSecret;

  @Bean
  RouteLocator routeLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("authentication_via_credentials",
            route -> route.path(ServiceUriProvider.AUTHENTICATION_PATH + "/login/credentials")
                .and().method(HttpMethod.POST)
                .filters(filter -> filter.stripPrefix(1)
                    .setPath(OAUTH_URL)
                    .addRequestParameter(GRANT_TYPE, PASSWORD)
                    .addRequestParameter(SCOPE, SCOPE_READ)
                    .filter(authorizationToParamsGatewayFilterFactory.apply(new Config()))
                    .setRequestHeader(HttpHeaders.AUTHORIZATION, gatewayClientSecret))
                .uri(serviceUriProvider.getAuthenticationUri())
        )
        .route("authentication_via_refresh_token",
            route -> route.path(ServiceUriProvider.AUTHENTICATION_PATH + "/login/refresh")
                .and().method(HttpMethod.POST)
                .filters(filter -> filter.stripPrefix(1)
                    .setPath(OAUTH_URL)
                    .addRequestParameter(GRANT_TYPE, REFRESH_TOKEN)
                    .addRequestParameter(SCOPE, SCOPE_READ)
                    .filter(authorizationToParamsGatewayFilterFactory.apply(new Config()))
                    .setRequestHeader(HttpHeaders.AUTHORIZATION, gatewayClientSecret))
                .uri(serviceUriProvider.getAuthenticationUri())
        )
        .route("authentication_service_requests",
            route -> route.path(ServiceUriProvider.AUTHENTICATION_PATH + "/**")
                .filters(filter -> filter.stripPrefix(1))
                .uri(serviceUriProvider.getAuthenticationUri())
        )
        .route("account_get",
            route -> route
                .path(ServiceUriProvider.ACCOUNT_PATH + "/**")
                .filters(filter -> filter.stripPrefix(1))
                .uri(serviceUriProvider.getAccountUri()))
        .route("registration",
            route -> route
                .path(ServiceUriProvider.REGISTRATION_PATH + "/**")
                .filters(filter -> filter.stripPrefix(1))
                .uri(serviceUriProvider.getRegistrationUri()))
        .route("notification",
            route -> route
                .path(ServiceUriProvider.NOTIFICATION_PATH + "/**")
                .filters(filter -> filter.stripPrefix(1))
                .uri(serviceUriProvider.getNotificationUri()))
        .route("client_get",
            route -> route
                .path("/", "/client/**")
                .and().method(HttpMethod.GET)
                .filters(filter -> filter.stripPrefix(1))
                .uri(serviceUriProvider.getClientUri()))
        .route("img_get",
            route -> route
                .path("/img/**")
                .and().method(HttpMethod.GET)
                .uri(serviceUriProvider.getClientUri()))
        .build();
  }
}
