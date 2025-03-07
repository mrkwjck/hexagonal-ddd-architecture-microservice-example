package mrkwjck.domain.account.exception;

import lombok.Getter;
import org.iban4j.Iban;

@Getter
public class AccountNotFoundException extends RuntimeException {

    private final String accountNumber;

    public AccountNotFoundException(String accountNumber) {
        super("Account not found by IBAN number %s".formatted(accountNumber));
        this.accountNumber = accountNumber;
    }

    public AccountNotFoundException(Iban accountNumber) {
        this(accountNumber.toString());
    }
}
