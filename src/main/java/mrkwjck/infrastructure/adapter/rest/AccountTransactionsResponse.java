package mrkwjck.infrastructure.adapter.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import mrkwjck.application.port.in.AccountTransaction;

@Getter
class AccountTransactionsResponse {

    @JsonValue
    private final List<AccountTransactionDto> accountTransactions;

    @JsonCreator
    public AccountTransactionsResponse(List<AccountTransaction> transactions) {
        accountTransactions = transactions.stream()
                .map(transaction ->
                        new AccountTransactionDto(transaction.type(), transaction.amount(), transaction.creationTime()))
                .toList();
    }

    private record AccountTransactionDto(String type, BigDecimal amount, LocalDateTime creationTime) {}
}
