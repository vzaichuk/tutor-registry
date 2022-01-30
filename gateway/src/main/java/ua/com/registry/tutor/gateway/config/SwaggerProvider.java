package ua.com.registry.tutor.gateway.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import ua.com.registry.tutor.common.service.ServiceUriProvider;

@Primary
@Component
@RequiredArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

  public static final String API_URI = "/v2/api-docs";

  private List<SwaggerResource> resources = null;

  @Override
  public List<SwaggerResource> get() {
    if (resources == null) {
      resources = getUriProviders().entrySet().stream()
          .map(entry -> swaggerResource(entry.getKey(), entry.getValue().get()))
          .collect(Collectors.toList());
    }
    return resources;
  }

  private Map<String, Supplier<String>> getUriProviders() {
    return new HashMap<>() {
      {
        put("Account", () -> ServiceUriProvider.ACCOUNT_PATH + API_URI);
        put("Registration", () -> ServiceUriProvider.REGISTRATION_PATH + API_URI);
        put("Notification", () -> ServiceUriProvider.NOTIFICATION_PATH + API_URI);
      }
    };
  }

  private SwaggerResource swaggerResource(String name, String location) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion("2.0");
    return swaggerResource;
  }
}
