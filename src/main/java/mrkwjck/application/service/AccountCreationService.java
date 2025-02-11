package mrkwjck.application.service;

import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.AccountDetails;
import mrkwjck.application.port.in.CreateAccountCommand;
import mrkwjck.application.port.in.CreateAccountUseCase;
import mrkwjck.domain.AccountDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
class AccountCreationService implements CreateAccountUseCase {

    private final AccountDomainService accountDomainService;

    @Override
    @Transactional
    public AccountDetails execute(CreateAccountCommand command) {
        var account = accountDomainService.createAccount(command.ownerName());
        return new AccountDetails(account.getAccountNumber(), account.getOwnerName(), account.getCurrency(), account.getBalance());
    }

}
