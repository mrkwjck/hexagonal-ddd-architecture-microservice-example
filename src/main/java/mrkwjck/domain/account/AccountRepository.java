package mrkwjck.domain.account;

import java.util.List;
import mrkwjck.domain.account.model.Account;
import org.iban4j.Iban;

public interface AccountRepository {

    void save(Account account);

    Account findByAccountNumber(Iban accountNumber);

    List<Account> findAll();
}
