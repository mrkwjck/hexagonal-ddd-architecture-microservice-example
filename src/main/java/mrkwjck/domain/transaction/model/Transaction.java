package mrkwjck.domain.transaction.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.iban4j.Iban;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AggregateRoot
@RequiredArgsConstructor
public class Transaction {

    @Identity
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
