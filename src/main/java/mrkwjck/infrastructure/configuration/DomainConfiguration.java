package mrkwjck.infrastructure.configuration;

import mrkwjck.domain.AccountDomainService;
import mrkwjck.domain.AccountTransactionDomainService;
import mrkwjck.domain.DomainEventPublisher;
import mrkwjck.domain.account.AccountRepository;
import mrkwjck.domain.transaction.TransactionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DomainConfiguration {

    @Bean
    AccountTransactionDomainService accountTransactionDomainService(AccountRepository accountRepository,
                                                                    TransactionRepository transactionRepository,
                                                                    DomainEventPublisher domainEventPublisher) {
        return new AccountTransactionDomainService(accountRepository, transactionRepository, domainEventPublisher);
    }

    @Bean
    AccountDomainService accountDomainService(AccountRepository accountRepository, DomainEventPublisher domainEventPublisher) {
        return new AccountDomainService(accountRepository, domainEventPublisher);
    }

    @Bean
    DomainEventPublisher domainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return applicationEventPublisher::publishEvent;
    }

}
