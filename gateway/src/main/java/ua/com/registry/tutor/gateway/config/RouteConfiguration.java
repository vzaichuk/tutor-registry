package ua.com.registry.tutor.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class RouteConfiguration {

  @Value("${ACCOUNT_HOST}")
  private String accountServiceName;

  @Bean
  RouteLocator routeLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("account_hello",
            route -> route.path("/account/**")
                .and()
                .method(HttpMethod.GET)
                .filters(filter -> filter.stripPrefix(1))
                .uri("lb://" + accountServiceName))
        .build();
  }
}
