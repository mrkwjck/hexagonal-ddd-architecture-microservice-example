package mrkwjck.infrastructure.adapter.rest;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import mrkwjck.application.port.in.AccountTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
class AccountTransactionsResponse {

    @JsonValue
    private final List<AccountTransactionDto> accountTransactions;

    public AccountTransactionsResponse(List<AccountTransaction> transactions) {
        accountTransactions = transactions.stream()
                .map(transaction -> new AccountTransactionDto(transaction.type(), transaction.amount(), transaction.creationTime()))
                .toList();
    }

    private record AccountTransactionDto(String type, BigDecimal amount, LocalDateTime creationTime) {
    }

}
