package mrkwjck.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.AccountTransaction;
import mrkwjck.application.port.in.AccountTransactionsQuery;
import mrkwjck.application.port.in.GetAccountTransactionsUseCase;
import mrkwjck.domain.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AccountTransactionsService implements GetAccountTransactionsUseCase {

    private final TransactionRepository transactionRepository;

    @Override
    public List<AccountTransaction> execute(AccountTransactionsQuery query) {
        return transactionRepository.findByAccountNumber(query.accountNumber()).stream()
                .map(AccountTransaction::new)
                .toList();
    }
}
