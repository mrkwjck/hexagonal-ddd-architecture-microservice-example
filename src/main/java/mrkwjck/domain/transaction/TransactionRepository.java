package mrkwjck.domain.transaction;

import java.util.List;
import mrkwjck.domain.transaction.model.Transaction;
import org.iban4j.Iban;

public interface TransactionRepository {

    void save(Transaction transaction);

    List<Transaction> findByAccountNumber(Iban accountNumber);
}
