package mrkwjck.domain.transaction.model

import org.iban4j.Iban
import spock.lang.Specification

class TransactionTest extends Specification {

    void "should create transaction"() {
        when:
        Transaction transaction = Transaction."$method"(Iban.valueOf(accountNumber), amount)

        then:
        transaction.getAccountNumber().toString() == accountNumber
        transaction.getAmount() == amount
        transaction.getType() == type

        where:
        method         | type                       | accountNumber                  | amount
        "ofDeposit"    | TransactionType.DEPOSIT    | "PL84109024029786112948743125" | new BigDecimal("10.00")
        "ofWithdrawal" | TransactionType.WITHDRAWAL | "PL35109024021684876364815598" | new BigDecimal("20.00")
    }


}
