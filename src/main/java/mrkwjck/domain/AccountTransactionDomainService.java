package mrkwjck.domain;

import java.math.BigDecimal;

import org.iban4j.Iban;

import lombok.RequiredArgsConstructor;
import mrkwjck.domain.account.AccountRepository;
import mrkwjck.domain.account.event.FundsDeposited;
import mrkwjck.domain.account.event.FundsWithdrawn;
import mrkwjck.domain.transaction.TransactionRepository;
import mrkwjck.domain.transaction.model.Transaction;


@RequiredArgsConstructor
public class AccountTransactionDomainService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final DomainEventPublisher domainEventPublisher;

    public void depositFunds(Iban accountNumber, BigDecimal amount) {
        var account = accountRepository.findByAccountNumber(accountNumber);
        account.deposit(amount);
        accountRepository.save(account);
        transactionRepository.save(Transaction.ofDeposit(accountNumber, amount));
        domainEventPublisher.publishEvent(new FundsDeposited(accountNumber, amount));
    }

    public void withdrawFunds(Iban accountNumber, BigDecimal amount) {
        var account = accountRepository.findByAccountNumber(accountNumber);
        account.withdraw(amount);
        accountRepository.save(account);
        transactionRepository.save(Transaction.ofWithdrawal(accountNumber, amount));
        domainEventPublisher.publishEvent(new FundsWithdrawn(accountNumber, amount));
    }

    public void transferFunds(Iban sourceAccountNumber, Iban targetAccountNumber, BigDecimal amount) {
        withdrawFunds(sourceAccountNumber, amount);
        depositFunds(targetAccountNumber, amount);
    }

}
