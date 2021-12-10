package ua.com.registry.tutor.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

@Component
public class BodyToParamsGatewayFilterFactory
    extends AbstractGatewayFilterFactory<BodyToParamsGatewayFilterFactory.Config> {

  public static final String BODY_ATTR = ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR+"Object";

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      List<String> types = exchange.getRequest().getHeaders().get(HttpHeaders.CONTENT_TYPE);
      if (Objects.isNull(types) || !types.contains(MediaType.APPLICATION_JSON_VALUE)) {
        return chain.filter(exchange);
      }
      String body = exchange.getAttribute(BODY_ATTR);
      if (Objects.isNull(body)) {
        throw new IllegalStateException("Request body is empty");
      }

      ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {

        @Override
        public URI getURI() {
          return UriComponentsBuilder.fromUri(exchange.getRequest().getURI()).queryParams(
              convertJsonToQueryParamMap(body, config)).build().toUri();
        }

        @Override
        public Flux<DataBuffer> getBody() {
          return Flux.empty();
        }
      };

      return chain.filter(exchange.mutate().request(mutatedRequest).build());
    };
  }

  @Data
  @AllArgsConstructor
  public static class Config {

    private Set<String> params;
  }

  public MultiValueMap<String, String> convertJsonToQueryParamMap(String json, Config config) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = null;

    try {
      jsonNode = mapper.readTree(json);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Body is not correct");
    }

    if (Objects.isNull(jsonNode)) {
      throw new IllegalStateException("Body is empty");
    }
    Iterator<Entry<String, JsonNode>> fields = jsonNode.fields();

    while (fields.hasNext()) {
      Map.Entry<String, JsonNode> entry = fields.next();
      if (config.getParams().contains(entry.getKey())) {
        multiValueMap.add(entry.getKey(), entry.getValue().asText());
      }
    }

    return multiValueMap;
  }
}
