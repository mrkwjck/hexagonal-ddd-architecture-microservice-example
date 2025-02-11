package mrkwjck.domain.transaction;

import mrkwjck.domain.transaction.model.Transaction;
import org.iban4j.Iban;
import org.jmolecules.ddd.annotation.Repository;

import java.util.List;

@Repository
public interface TransactionRepository {

    void save(Transaction transaction);

    List<Transaction> findByAccountNumber(Iban accountNumber);

}
