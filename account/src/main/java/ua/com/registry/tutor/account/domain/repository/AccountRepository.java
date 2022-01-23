package ua.com.registry.tutor.account.domain.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.registry.tutor.account.domain.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

  Optional<Account> findOneByUserId(int userId);

  Page<Account> findAllByStatusOrderById(int status, Pageable pageable);

  Page<Account> findAllByRoleAndStatus(String role, int status, Pageable pageable);
}
