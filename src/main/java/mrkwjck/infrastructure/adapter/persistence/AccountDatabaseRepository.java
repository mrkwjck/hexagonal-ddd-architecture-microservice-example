package mrkwjck.infrastructure.adapter.persistence;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import mrkwjck.domain.account.AccountRepository;
import mrkwjck.domain.account.exception.AccountNotFoundException;
import mrkwjck.domain.account.model.Account;
import org.iban4j.Iban;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class AccountDatabaseRepository implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public void save(Account account) {
        Objects.requireNonNull(account);
        var accountJpaEntity = accountJpaRepository
                .findByAccountNumber(account.getAccountNumber().toString())
                .map(entity -> {
                    var updatedAccountJpaEntity = AccountJpaEntityMapper.INSTANCE.toAccountJpaEntity(account);
                    updatedAccountJpaEntity.setId(entity.getId());
                    return updatedAccountJpaEntity;
                })
                .orElse(AccountJpaEntityMapper.INSTANCE.toAccountJpaEntity(account));
        accountJpaRepository.save(accountJpaEntity);
    }

    @Override
    public Account findByAccountNumber(Iban accountNumber) {
        return accountJpaRepository
                .findByAccountNumber(accountNumber.toString())
                .map(AccountJpaEntityMapper.INSTANCE::toAccountDomain)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }
}
