package mrkwjck.infrastructure.adapter.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mrkwjck.domain.account.exception.AccountNotFoundException;
import mrkwjck.domain.transaction.TransactionRepository;
import mrkwjck.domain.transaction.model.Transaction;
import org.iban4j.Iban;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class TransactionDatabaseRepository implements TransactionRepository {

    private final AccountJpaRepository accountJpaRepository;
    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public void save(Transaction transaction) {
        var accountNumber = transaction.getAccountNumber().toString();
        var accountJpaEntity = accountJpaRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
        var transactionJpaEntity =
                TransactionJpaEntityMapper.INSTANCE.toTransactionJpaEntity(transaction, accountJpaEntity);
        transactionJpaRepository.save(transactionJpaEntity);
    }

    @Override
    public List<Transaction> findByAccountNumber(Iban accountNumber) {
        return transactionJpaRepository.findByAccountNumber(accountNumber.toString()).stream()
                .map(TransactionJpaEntityMapper.INSTANCE::toTransaction)
                .toList();
    }
}
