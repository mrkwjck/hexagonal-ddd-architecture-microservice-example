package mrkwjck.infrastructure.adapter.rest;

import java.math.BigDecimal;
import java.util.Currency;

import mrkwjck.application.port.in.AccountDetails;


record AccountDetailsResponse(String accountNumber, String ownerName, Currency currency, BigDecimal balance) {

    public AccountDetailsResponse(AccountDetails accountDetails) {
        this(accountDetails.accountNumber().toString(), accountDetails.ownerName(), accountDetails.currency(), accountDetails.balance());
    }

}
