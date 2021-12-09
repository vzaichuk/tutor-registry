package ua.com.registry.tutor.authentication.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.registry.tutor.authentication.domain.entity.AuthClient;

public interface AuthClientRepository extends JpaRepository<AuthClient, Integer> {

  Optional<AuthClient> findOneByClient(String clientId);
}
