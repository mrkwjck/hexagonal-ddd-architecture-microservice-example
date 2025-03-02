package mrkwjck.domain;

import lombok.RequiredArgsConstructor;
import mrkwjck.domain.account.AccountRepository;
import mrkwjck.domain.account.event.AccountCreated;
import mrkwjck.domain.account.model.Account;


@RequiredArgsConstructor
public class AccountDomainService {

    private final AccountRepository accountRepository;
    private final DomainEventPublisher domainEventPublisher;

    public Account createAccount(String ownerName) {
        var account = new Account(ownerName);
        accountRepository.save(account);
        domainEventPublisher.publishEvent(new AccountCreated(account.getAccountNumber()));
        return account;
    }

}
