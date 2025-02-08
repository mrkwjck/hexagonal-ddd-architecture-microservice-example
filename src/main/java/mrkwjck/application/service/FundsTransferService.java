package mrkwjck.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.TransferFundsCommand;
import mrkwjck.application.port.in.TransferFundsUseCase;
import mrkwjck.domain.AccountTransactionDomainService;


@Service
@RequiredArgsConstructor
class FundsTransferService implements TransferFundsUseCase {

    private final AccountTransactionDomainService accountTransactionDomainService;

    @Override
    @Transactional
    public void execute(TransferFundsCommand command) {
        accountTransactionDomainService.transferFunds(command.sourceAccountIban(), command.targetAccountIban(), command.amount());
    }

}
