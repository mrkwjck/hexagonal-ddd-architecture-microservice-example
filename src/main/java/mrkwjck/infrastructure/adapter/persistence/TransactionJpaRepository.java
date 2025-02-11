package mrkwjck.infrastructure.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long> {

    @Query("select t from TransactionJpaEntity t where t.account.accountNumber = :accountNumber order by t.creationTime desc")
    List<TransactionJpaEntity> findByAccountNumber(String accountNumber);

}
