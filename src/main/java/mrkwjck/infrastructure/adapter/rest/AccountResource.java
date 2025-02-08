package mrkwjck.infrastructure.adapter.rest;

import org.iban4j.Iban;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mrkwjck.application.port.in.AccountDetailsQuery;
import mrkwjck.application.port.in.CreateAccountCommand;
import mrkwjck.application.port.in.CreateAccountUseCase;
import mrkwjck.application.port.in.DepositFundsCommand;
import mrkwjck.application.port.in.DepositFundsUseCase;
import mrkwjck.application.port.in.GetAccounDetailsUseCase;
import mrkwjck.application.port.in.WithdrawFundsCommand;
import mrkwjck.application.port.in.WithdrawFundsUseCase;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
class AccountResource {

    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccounDetailsUseCase getAccounDetailsUseCase;
    private final DepositFundsUseCase depositFundsUseCase;
    private final WithdrawFundsUseCase withdrawFundsUseCase;

    @PostMapping
    public ResponseEntity<AccountDetailsResponse> createAccount(@RequestBody CreateAccountRequest request) {
        var accountDetails = createAccountUseCase.execute(new CreateAccountCommand(request.ownerName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountDetailsResponse(accountDetails));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDetailsResponse> getAccountDetails(@PathVariable String accountNumber) {
        var accountDetails = getAccounDetailsUseCase.execute(new AccountDetailsQuery(Iban.valueOf(accountNumber)));
        return ResponseEntity.ok(new AccountDetailsResponse(accountDetails));
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<Void> depositFunds(@PathVariable String accountNumber, @RequestBody DepositFundsRequest request) {
        depositFundsUseCase.execute(new DepositFundsCommand(Iban.valueOf(accountNumber), request.amount()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountNumber}/withdrawal")
    public ResponseEntity<Void> withdrawFunds(@PathVariable String accountNumber, @RequestBody WithdrawFundsRequest request) {
        withdrawFundsUseCase.execute(new WithdrawFundsCommand(Iban.valueOf(accountNumber), request.amount()));
        return ResponseEntity.ok().build();
    }

}
