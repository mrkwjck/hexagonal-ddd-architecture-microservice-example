package mrkwjck.infrastructure.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;


interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long> {
}
