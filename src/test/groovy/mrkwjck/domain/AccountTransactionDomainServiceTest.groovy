package mrkwjck.domain

import mrkwjck.domain.account.AccountRepository
import mrkwjck.domain.account.event.FundsDeposited
import mrkwjck.domain.account.event.FundsWithdrawn
import mrkwjck.domain.account.model.Account
import mrkwjck.domain.transaction.TransactionRepository
import mrkwjck.domain.transaction.model.Transaction
import mrkwjck.domain.transaction.model.TransactionType
import org.iban4j.Iban
import spock.lang.Specification

class AccountTransactionDomainServiceTest extends Specification {

    void "should handle deposit transaction"() {
        given:
        var accountRepository = Mock(AccountRepository)
        var transactionRepository = Mock(TransactionRepository)
        var domainEventPublisher = Mock(DomainEventPublisher)
        var account = createAccount("Jan Kowalski")
        var accountTransactionDomainService = new AccountTransactionDomainService(
                accountRepository, transactionRepository, domainEventPublisher)
        var transactionAmount = new BigDecimal("100.00")

        accountRepository.findByAccountNumber(_) >> account

        when:
        accountTransactionDomainService.depositFunds(account.getAccountNumber(), transactionAmount)

        then:
        1 * accountRepository.save(_) >> {
            var savedAccount = (Account) it[0]
            assert savedAccount.getBalance() == transactionAmount
        }
        1 * transactionRepository.save(_) >> {
            var savedTransaction = (Transaction) it[0]
            assert savedTransaction.getAmount() == transactionAmount
            assert savedTransaction.getType() == TransactionType.DEPOSIT
        }
        1 * domainEventPublisher.publishEvent(_) >> {
            var event = (FundsDeposited) it[0]
            assert event.getAccountNumber() == account.getAccountNumber().toString()
            assert event.getAmount() == transactionAmount
        }
    }

    void "should handle withdrawal transaction"() {
        given:
        var accountRepository = Mock(AccountRepository)
        var transactionRepository = Mock(TransactionRepository)
        var domainEventPublisher = Mock(DomainEventPublisher)
        var account = createAccount("Jan Kowalski")
        var accountTransactionDomainService = new AccountTransactionDomainService(
                accountRepository, transactionRepository, domainEventPublisher)
        var transactionAmount = new BigDecimal("100.00")
        account.deposit(transactionAmount)

        accountRepository.findByAccountNumber(_) >> account

        when:
        accountTransactionDomainService.withdrawFunds(account.getAccountNumber(), transactionAmount)

        then:
        1 * accountRepository.save(_) >> {
            var savedAccount = (Account) it[0]
            assert savedAccount.getBalance() == BigDecimal.ZERO
        }
        1 * transactionRepository.save(_) >> {
            var savedTransaction = (Transaction) it[0]
            assert savedTransaction.getAmount() == transactionAmount
            assert savedTransaction.getType() == TransactionType.WITHDRAWAL
        }
        1 * domainEventPublisher.publishEvent(_) >> {
            var event = (FundsWithdrawn) it[0]
            assert event.getAccountNumber() == account.getAccountNumber().toString()
            assert event.getAmount() == transactionAmount
        }
    }

    void "should handle transferring funds between accounts"() {
        given:
        var accountRepository = Mock(AccountRepository)
        var transactionRepository = Mock(TransactionRepository)
        var domainEventPublisher = Mock(DomainEventPublisher)
        var accountTransactionDomainService = new AccountTransactionDomainService(
                accountRepository, transactionRepository, domainEventPublisher)
        var sourceAccount = createAccount("Jan Kowalski")
        var targetAccount = createAccount("Maria Kowalska")
        var transactionAmount = new BigDecimal("100.00")

        accountRepository.findByAccountNumber(sourceAccount.getAccountNumber()) >> sourceAccount
        accountRepository.findByAccountNumber(targetAccount.getAccountNumber()) >> targetAccount

        sourceAccount.deposit(new BigDecimal("1000.00"))

        var savedAccounts = new ArrayList<Account>()

        when:
        accountTransactionDomainService.transferFunds(
                sourceAccount.getAccountNumber(), targetAccount.getAccountNumber(), transactionAmount)

        then:
        2 * accountRepository.save(_) >> {
            savedAccounts.add((Account) it[0])
        }
        savedAccountBalanceIsOk(savedAccounts, sourceAccount)
        savedAccountBalanceIsOk(savedAccounts, targetAccount)
    }

    static Account createAccount(String ownerName = "Jan Kowalski") {
        return new Account(ownerName)
    }

    static boolean savedAccountBalanceIsOk(ArrayList<Account> savedAccounts, Account referenceAccount) {
        return savedAccounts.find(account -> account.getAccountNumber() == referenceAccount.getAccountNumber())
                .getBalance() == referenceAccount.getBalance()
    }

}
