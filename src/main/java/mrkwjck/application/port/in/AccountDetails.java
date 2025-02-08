package mrkwjck.application.port.in;

import java.math.BigDecimal;
import java.util.Currency;

import org.iban4j.Iban;

import mrkwjck.domain.account.model.Account;


public record AccountDetails(Iban accountNumber, String ownerName, Currency currency, BigDecimal balance) {

    public AccountDetails(Account account) {
        this(account.getAccountNumber(), account.getOwnerName(), account.getCurrency(), account.getBalance());
    }

}
