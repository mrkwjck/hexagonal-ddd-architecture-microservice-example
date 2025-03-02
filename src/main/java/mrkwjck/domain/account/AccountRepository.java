package mrkwjck.domain.account;

import org.iban4j.Iban;

import mrkwjck.domain.account.model.Account;


public interface AccountRepository {

    void save(Account account);

    Account findByAccountNumber(Iban id);

}
