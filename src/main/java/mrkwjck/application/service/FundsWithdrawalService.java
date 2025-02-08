package mrkwjck.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.WithdrawFundsCommand;
import mrkwjck.application.port.in.WithdrawFundsUseCase;
import mrkwjck.domain.AccountTransactionDomainService;


@Service
@RequiredArgsConstructor
class FundsWithdrawalService implements WithdrawFundsUseCase {

    private final AccountTransactionDomainService accountTransactionDomainService;

    @Override
    @Transactional
    public void execute(WithdrawFundsCommand command) {
        accountTransactionDomainService.withdrawFunds(command.accountNumber(), command.amount());
    }

}
