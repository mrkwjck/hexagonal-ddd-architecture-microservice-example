package mrkwjck.infrastructure.adapter.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {

    Optional<AccountJpaEntity> findByAccountNumber(String accountNumber);
}
