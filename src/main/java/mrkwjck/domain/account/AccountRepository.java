package mrkwjck.domain.account;

import mrkwjck.domain.account.model.Account;
import org.iban4j.Iban;
import org.jmolecules.ddd.annotation.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository {

    void save(Account account);

    Account findByAccountNumber(Iban id);

}
