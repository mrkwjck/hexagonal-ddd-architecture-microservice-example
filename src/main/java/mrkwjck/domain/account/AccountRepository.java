package mrkwjck.domain.account;

import mrkwjck.domain.account.model.Account;
import org.iban4j.Iban;

public interface AccountRepository {

    void save(Account account);

    Account findByAccountNumber(Iban accountNumber);
}
