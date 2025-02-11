package mrkwjck.application.service;

import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.AccountTransaction;
import mrkwjck.application.port.in.AccountTransactionsQuery;
import mrkwjck.application.port.in.GetAccountTransactionsUseCase;
import mrkwjck.domain.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class AccountTransactionsService implements GetAccountTransactionsUseCase {

    private final TransactionRepository transactionRepository;

    @Override
    public List<AccountTransaction> execute(AccountTransactionsQuery query) {
        return transactionRepository.findByAccountNumber(query.accountNumber())
                .stream()
                .map(AccountTransaction::new)
                .toList();
    }

}
