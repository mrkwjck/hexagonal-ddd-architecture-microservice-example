package mrkwjck.application.port.in;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import mrkwjck.domain.transaction.model.Transaction;

public record AccountTransaction(String accountNumber, String type, BigDecimal amount, LocalDateTime creationTime) {

    public AccountTransaction(Transaction transaction) {
        this(
                transaction.getAccountNumber().toString(),
                transaction.getType().name(),
                transaction.getAmount(),
                transaction.getCreationTime());
    }
}
