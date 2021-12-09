package ua.com.registry.tutor.authentication.config.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import ua.com.registry.tutor.authentication.domain.entity.AuthClient;
import ua.com.registry.tutor.authentication.domain.enums.GrantType;
import ua.com.registry.tutor.authentication.service.AuthClientService;

@RequiredArgsConstructor
public class CustomClientDetailsService implements ClientDetailsService {

  private final AuthClientService authClientService;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    return authClientService.findOneByClientId(clientId)
        .filter(AuthClient::isEnabled)
        .map(Client::new)
        .orElseThrow(() -> new BadCredentialsException("Bad auth credentials"));
  }

  @RequiredArgsConstructor
  static class Client implements ClientDetails {

    private final AuthClient authClient;

    @Override
    public String getClientId() {
      return authClient.getClient();
    }

    @Override
    public Set<String> getResourceIds() {
      return Collections.emptySet();
    }

    @Override
    public boolean isSecretRequired() {
      return true;
    }

    @Override
    public String getClientSecret() {
      return authClient.getSecret();
    }

    @Override
    public boolean isScoped() {
      return false;
    }

    @Override
    public Set<String> getScope() {
      return Collections.emptySet();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
      return authClient.getGrantTypes().stream()
          .map(GrantType::getValue).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
      return Collections.emptySet();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
      return Collections.emptySet();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
      return authClient.getAccessTokenValidityDuration();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
      return authClient.getRefreshTokenValidityDuration();
    }

    @Override
    public boolean isAutoApprove(String scope) {
      return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
      return Collections.emptyMap();
    }
  }
}
