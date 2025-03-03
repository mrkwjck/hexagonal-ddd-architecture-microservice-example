package mrkwjck.domain.account.model;

import java.math.BigDecimal;
import java.util.Currency;
import lombok.Builder;
import lombok.Getter;
import mrkwjck.domain.account.exception.InsufficientAccountBalanceException;
import mrkwjck.domain.account.exception.InvalidTransactionAmountException;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

@Getter
@Builder
public class Account {

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
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionAmountException("Withdrawal amount must be greater than zero");
        }
        var resultingBalance = balance.subtract(amount);
        if (resultingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientAccountBalanceException("Insufficient funds in the account");
        }
        balance = balance.subtract(amount);
    }
}
