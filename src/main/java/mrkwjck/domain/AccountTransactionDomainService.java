package mrkwjck.domain;

import lombok.RequiredArgsConstructor;
import mrkwjck.domain.account.AccountRepository;
import mrkwjck.domain.transaction.TransactionRepository;
import mrkwjck.domain.transaction.model.Transaction;
import org.iban4j.Iban;
import org.jmolecules.ddd.annotation.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class AccountTransactionDomainService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public void depositFunds(Iban accountNumber, BigDecimal amount) {
        var account = accountRepository.findByAccountNumber(accountNumber);
        account.deposit(amount);
        accountRepository.save(account);
        transactionRepository.save(Transaction.ofDeposit(accountNumber, amount));
    }

    public void withdrawFunds(Iban accountNumber, BigDecimal amount) {
        var account = accountRepository.findByAccountNumber(accountNumber);
        account.withdraw(amount);
        accountRepository.save(account);
        transactionRepository.save(Transaction.ofWithdrawal(accountNumber, amount));
    }

    public void transferFunds(Iban sourceAccountNumber, Iban targetAccountNumber, BigDecimal amount) {
        withdrawFunds(sourceAccountNumber, amount);
        depositFunds(targetAccountNumber, amount);
    }

}
