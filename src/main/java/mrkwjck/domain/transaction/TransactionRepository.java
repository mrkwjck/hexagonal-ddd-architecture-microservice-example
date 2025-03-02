package mrkwjck.domain.transaction;

import java.util.List;

import org.iban4j.Iban;

import mrkwjck.domain.transaction.model.Transaction;


public interface TransactionRepository {

    void save(Transaction transaction);

    List<Transaction> findByAccountNumber(Iban accountNumber);

}
