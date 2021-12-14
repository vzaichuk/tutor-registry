package ua.com.registry.tutor.gateway.filter;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AuthorizationToParamsGatewayFilterFactory
    extends AbstractGatewayFilterFactory<AuthorizationToParamsGatewayFilterFactory.Config> {

  private Handler handler;

  @PostConstruct
  public void init() {
    handler = new BasicHandler(new BearerHandler());
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      List<String> values = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
      if (Objects.isNull(values) || values.isEmpty()) {
        return chain.filter(exchange);
      }

      String authorization = values.iterator().next();

      ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {

        @Override
        public URI getURI() {
          return UriComponentsBuilder
              .fromUri(exchange.getRequest().getURI())
              .queryParams(handler.handle(authorization, config))
              .build().toUri();
        }
      };

      return chain.filter(exchange.mutate().request(mutatedRequest).build());
    };
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Config {

    private String tokenKey = "refresh_token";
  }

  private interface Handler {

    MultiValueMap<String, String> handle(String authorization, Config config);
  }

  @NoArgsConstructor
  private abstract class AbstractHandler implements Handler {

    private Handler nextHandler;

    public AbstractHandler(Handler handler) {
      nextHandler = handler;
    }

    abstract boolean notSupports(String authorization);

    protected MultiValueMap<String, String> next(String authorization, Config config) {
      if (Objects.nonNull(nextHandler)) {
        return nextHandler.handle(authorization, config);
      }
      return null;
    }
  }

  @NoArgsConstructor
  private class BasicHandler extends AbstractHandler {

    public BasicHandler(Handler handler) {
      super(handler);
    }

    @Override
    public MultiValueMap<String, String> handle(String authorization, Config config) {
      if (notSupports(authorization)) {
        return next(authorization, config);
      }

      // Authorization: Basic base64credentials
      String base64Credentials = authorization.substring("Basic".length()).trim();
      byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
      String credentials = new String(credDecoded, StandardCharsets.UTF_8);
      final String[] values = credentials.split(":", 2);

      if (values.length != 2) {
        throw new IllegalStateException("Bad basic authorization credentials");
      }

      MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
      result.add("username", values[0]);
      result.add("password", values[1]);
      return result;
    }

    @Override
    boolean notSupports(String authorization) {
      return Objects.isNull(authorization) || !authorization.toLowerCase().startsWith("basic");
    }
  }

  @NoArgsConstructor
  private class BearerHandler extends AbstractHandler {

    public BearerHandler(Handler handler) {
      super(handler);
    }

    @Override
    public MultiValueMap<String, String> handle(String authorization, Config config) {
      if (notSupports(authorization)) {
        return next(authorization, config);
      }

      // Authorization: Bearer token
      String token = authorization.substring("Bearer".length()).trim();

      MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
      result.add(config.getTokenKey(), token);
      return result;
    }

    @Override
    boolean notSupports(String authorization) {
      return Objects.isNull(authorization) || !authorization.toLowerCase().startsWith("bearer");
    }
  }
}
