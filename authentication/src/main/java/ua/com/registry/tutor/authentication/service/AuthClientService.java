package ua.com.registry.tutor.authentication.service;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.registry.tutor.authentication.domain.entity.AuthClient;
import ua.com.registry.tutor.authentication.domain.repository.AuthClientRepository;

@Service
@RequiredArgsConstructor
public class AuthClientService {

  private final AuthClientRepository authClientRepository;

  public Optional<AuthClient> findOneByClientId(String clientId) {
    return authClientRepository
        .findOneByClient(Objects.requireNonNull(clientId).trim().toLowerCase());
  }
}
