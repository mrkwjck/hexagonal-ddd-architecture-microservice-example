package mrkwjck.domain.account.model

import mrkwjck.domain.account.exception.InsufficientAccountBalanceException
import mrkwjck.domain.account.exception.InvalidTransactionAmountException
import org.iban4j.IbanUtil
import spock.lang.Specification

class AccountTest extends Specification {

    void "should create account with number and PLN currency"() {
        given:
        var ownerName = "John Mitchell"

        when:
        var account = createAccount(ownerName)

        then:
        account.getOwnerName() == ownerName
        account.getCurrency() == Currency.getInstance("PLN")
        account.getBalance() == BigDecimal.ZERO
        isValidIban(account.getAccountNumber().toString())
    }

    void "should deposit money to account"() {
        given:
        var account = createAccount()
        var depositAmount = new BigDecimal("10.23")

        when:
        account.deposit(depositAmount)
        account.deposit(depositAmount)

        then:
        account.getBalance() == 2*depositAmount
    }

    void "should throw exception when deposit amount is not positive"() {
        when:
        createAccount().deposit(depositAmount)

        then:
        thrown(InvalidTransactionAmountException)

        where:
        depositAmount << [new BigDecimal("-10"), BigDecimal.ZERO]
    }

    void "should withdraw money from account"() {
        given:
        var account = createAccount()
        var depositAmount = new BigDecimal("10.23")

        when:
        account.deposit(depositAmount)
        account.deposit(depositAmount)
        account.withdraw(2*depositAmount)

        then:
        account.getBalance() == BigDecimal.ZERO
    }

    void "should throw exception if withdrawal amount is not positive"() {
        when:
        createAccount().withdraw(depositAmount)

        then:
        thrown(InvalidTransactionAmountException)

        where:
        depositAmount << [new BigDecimal("-10"), BigDecimal.ZERO]
    }

    void "should throw exception if withdrawal amount results in negative balance"() {
        given:
        var account = createAccount()
        var depositAmount = new BigDecimal("10.23")

        when:
        account.deposit(depositAmount)
        account.deposit(depositAmount)
        account.withdraw(3*depositAmount)

        then:
        thrown(InsufficientAccountBalanceException)
    }

    static Account createAccount(String ownerName = "Jan Kowalski") {
        return new Account(ownerName)
    }

    static boolean isValidIban(String iban) {
        try {
            IbanUtil.validate(iban)
        } catch (Exception e) {
            return false
        }
        return true
    }

}
