package mrkwjck.domain.account.model;

import lombok.Builder;
import lombok.Getter;
import mrkwjck.domain.account.exception.InsufficientAccountBalanceException;
import mrkwjck.domain.account.exception.InvalidTransactionAmountException;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Getter
@Builder
@AggregateRoot
public class Account {

    @Identity
    private final Iban accountNumber;
    private final Currency currency;
    private BigDecimal balance;
    private final String ownerName;

    public Account(String ownerName) {
        this(Iban.random(CountryCode.PL), Currency.getInstance("PLN"), BigDecimal.ZERO, ownerName);
    }

    Account(Iban accountNumber, Currency currency, BigDecimal balance, String ownerName) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.balance = balance;
        this.ownerName = ownerName;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionAmountException("Deposit amount must be greater than zero");
        }
        balance = balance.add(amount);
        //TODO publish event DepositCompleted (create transaction) / DepositAttemptFailed
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidTransactionAmountException("Withdrawal amount must be greater than zero");
        }
        var resultingBalance = balance.subtract(amount);
        if (resultingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientAccountBalanceException("Insufficient funds in the account");
        }
        balance = balance.subtract(amount);
        //TODO publish event WithdrawalCompleted (create transaction) / WithdrawalAttemptFailed
    }

}
