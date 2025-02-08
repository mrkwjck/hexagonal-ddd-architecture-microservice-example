package mrkwjck.infrastructure.adapter.rest;

import org.iban4j.Iban;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.TransferFundsCommand;
import mrkwjck.application.port.in.TransferFundsUseCase;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transfers")
class TransferResource {

    private final TransferFundsUseCase transferFundsUseCase;

    @PostMapping
    public void transferFunds(@RequestBody TransferFundsRequest request) {
        transferFundsUseCase.execute(new TransferFundsCommand(
            Iban.valueOf(request.sourceAccountNumber()), Iban.valueOf(request.targetAccountNumber()), request.amount()));
    }

}
