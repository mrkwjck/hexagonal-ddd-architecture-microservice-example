package mrkwjck.domain.account.exception;

import org.iban4j.Iban;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountNumber) {
        super("Account not found by IBAN number %s".formatted(accountNumber));
    }

    public AccountNotFoundException(Iban accountNumber) {
        this(accountNumber.toString());
    }
}
