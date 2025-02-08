package mrkwjck.infrastructure.configuration;

import mrkwjck.domain.AccountDomainService;
import mrkwjck.domain.AccountTransactionDomainService;
import mrkwjck.domain.account.AccountRepository;
import mrkwjck.domain.transaction.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DomainConfiguration {

    @Bean
    AccountTransactionDomainService accountTransactionDomainService(AccountRepository accountRepository,
                                                         TransactionRepository transactionRepository) {
        return new AccountTransactionDomainService(accountRepository, transactionRepository);
    }

    @Bean
    AccountDomainService accountDomainService(AccountRepository accountRepository) {
        return new AccountDomainService(accountRepository);
    }

}
