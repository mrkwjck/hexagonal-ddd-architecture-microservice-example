package mrkwjck.domain

import mrkwjck.domain.account.AccountRepository
import mrkwjck.domain.account.event.AccountCreated
import mrkwjck.domain.account.model.Account
import spock.lang.Specification

class AccountDomainServiceTest extends Specification {

    void "should save created account and publish event for created account"() {
        given:
        var accountRepository = Mock(AccountRepository)
        var domainEventPublisher = Mock(DomainEventPublisher)
        var accountOwnerName = "Jan Kowalski"
        var accountDomainService = new AccountDomainService(accountRepository, domainEventPublisher)

        when:
        final var account = accountDomainService.createAccount(accountOwnerName)

        then:
        account.getOwnerName() == accountOwnerName
        1 * accountRepository.save(_) >> {
            var savedAccount = (Account) it[0]
            assert savedAccount.getBalance() == BigDecimal.ZERO
            assert savedAccount.getOwnerName() == accountOwnerName
        }
        1 * domainEventPublisher.publishEvent(_) >> {
            var event = (AccountCreated) it[0]
            assert event != null
        }
    }

}
