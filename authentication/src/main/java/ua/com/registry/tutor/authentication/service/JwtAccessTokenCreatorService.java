package ua.com.registry.tutor.authentication.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import ua.com.registry.tutor.authentication.domain.entity.User;

@Service
@RequiredArgsConstructor
public class JwtAccessTokenCreatorService {

  private final AuthorizationServerTokenServices authorizationServerTokenServices;

  public OAuth2AccessToken create(User user) {
    OAuth2Request request = new OAuth2Request(Collections.emptyMap(), "gateway",
        Collections.emptySet(), true, new HashSet<>(Collections.singletonList("read")),
        Collections.emptySet(), "", Collections.emptySet(),
        Collections.emptyMap());

    Authentication userAuthentication = new UsernamePasswordAuthenticationToken(
        user.getUsername(),
        "[HIDDEN]",
        user.getRoles().stream()
            .map(r -> new SimpleGrantedAuthority(r.getValue()))
            .collect(Collectors.toSet()));
    OAuth2Authentication authentication = new OAuth2Authentication(request, userAuthentication);

    return authorizationServerTokenServices.createAccessToken(authentication);
  }
}
