package mrkwjck.infrastructure.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {

    Optional<AccountJpaEntity> findByAccountNumber(String accountNumber);

}
