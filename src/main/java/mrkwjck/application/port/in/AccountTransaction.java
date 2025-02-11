package mrkwjck.application.port.in;

import mrkwjck.domain.transaction.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record AccountTransaction(String accountNumber, String type, BigDecimal amount, LocalDateTime creationTime) {

    public AccountTransaction(Transaction transaction) {
        this(transaction.getAccountNumber().toString(), transaction.getType().name(),
                transaction.getAmount(), transaction.getCreationTime());
    }

}
