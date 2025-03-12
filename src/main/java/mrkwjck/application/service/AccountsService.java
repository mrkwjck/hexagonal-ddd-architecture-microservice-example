package mrkwjck.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.AccountDetails;
import mrkwjck.application.port.in.GetAccountsUseCase;
import mrkwjck.domain.account.AccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AccountsService implements GetAccountsUseCase {

    private final AccountRepository accountRepository;

    @Override
    public List<AccountDetails> execute() {
        return accountRepository.findAll().stream().map(AccountDetails::new).toList();
    }
}
