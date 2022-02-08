package ua.com.registry.tutor.authentication.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.registry.tutor.authentication.config.Oauth2Properties;

@Service
@RequiredArgsConstructor
public class Oauth2ProviderFactoryService {

  private final Map<String, Oauth2ProviderService> services = new HashMap<>();
  private final Map<String, Class<? extends Oauth2ProviderService>> configuration =
      new HashMap<>() {{
        put("google", Oauth2GoogleProviderService.class);
      }};

  private final Oauth2Properties oauth2Properties;

  public Oauth2ProviderService build(String id) {
    if (!services.containsKey(id)) {
      if (!configuration.containsKey(id)) {
        throw new RuntimeException("Authorization provider is not supported");
      }
      try {
        services.put(id, configuration.get(id)
            .getConstructor(Oauth2Properties.class)
            .newInstance(oauth2Properties));
      } catch (NoSuchMethodException | IllegalAccessException
          | InvocationTargetException | InstantiationException e
      ) {
        throw new RuntimeException(e);
      }
    }

    return services.get(id);
  }
}
