package mrkwjck.application.service;

import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.AccountDetails;
import mrkwjck.application.port.in.AccountDetailsQuery;
import mrkwjck.application.port.in.GetAccounDetailsUseCase;
import mrkwjck.domain.account.AccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AccountDetailsService implements GetAccounDetailsUseCase {

    private final AccountRepository accountRepository;

    @Override
    public AccountDetails execute(AccountDetailsQuery query) {
        return new AccountDetails(accountRepository.findByAccountNumber(query.accountNumber()));
    }
}
