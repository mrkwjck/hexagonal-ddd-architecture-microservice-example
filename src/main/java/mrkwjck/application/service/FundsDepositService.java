package mrkwjck.application.service;

import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.DepositFundsCommand;
import mrkwjck.application.port.in.DepositFundsUseCase;
import mrkwjck.domain.AccountTransactionDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class FundsDepositService implements DepositFundsUseCase {

    private final AccountTransactionDomainService accountTransactionDomainService;

    @Override
    @Transactional
    public void execute(DepositFundsCommand command) {
        accountTransactionDomainService.depositFunds(command.accountNumber(), command.amount());
    }
}
