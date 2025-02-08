package mrkwjck.domain;

import lombok.RequiredArgsConstructor;
import mrkwjck.domain.account.AccountRepository;
import mrkwjck.domain.account.model.Account;
import org.jmolecules.ddd.annotation.Service;


@Service
@RequiredArgsConstructor
public class AccountDomainService {

    private final AccountRepository accountRepository;

    public Account createAccount(String ownerName) {
        var account = new Account(ownerName);
        accountRepository.save(account);
        return account;
    }

}
