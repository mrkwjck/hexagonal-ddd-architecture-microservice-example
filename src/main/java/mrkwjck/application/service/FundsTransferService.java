package mrkwjck.application.service;

import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.TransferFundsCommand;
import mrkwjck.application.port.in.TransferFundsUseCase;
import mrkwjck.domain.AccountTransactionDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class FundsTransferService implements TransferFundsUseCase {

    private final AccountTransactionDomainService accountTransactionDomainService;

    @Override
    @Transactional
    public void execute(TransferFundsCommand command) {
        accountTransactionDomainService.transferFunds(
                command.sourceAccountIban(), command.targetAccountIban(), command.amount());
    }
}
