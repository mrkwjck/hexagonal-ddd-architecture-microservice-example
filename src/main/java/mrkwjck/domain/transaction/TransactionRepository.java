package mrkwjck.domain.transaction;

import mrkwjck.domain.transaction.model.Transaction;
import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface TransactionRepository {

    void save(Transaction transaction);

}
