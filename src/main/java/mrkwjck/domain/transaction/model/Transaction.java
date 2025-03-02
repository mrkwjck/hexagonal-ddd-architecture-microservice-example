package mrkwjck.domain.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.iban4j.Iban;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Transaction {

    private final TransactionId id;
    private final Iban accountNumber;
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime creationTime;

    public static Transaction ofDeposit(Iban accountNumber, BigDecimal amount) {
        return new Transaction(new TransactionId(), accountNumber, TransactionType.DEPOSIT, amount, LocalDateTime.now());
    }

    public static Transaction ofWithdrawal(Iban accountNumber, BigDecimal amount) {
        return new Transaction(new TransactionId(), accountNumber, TransactionType.WITHDRAWAL, amount, LocalDateTime.now());
    }

}
