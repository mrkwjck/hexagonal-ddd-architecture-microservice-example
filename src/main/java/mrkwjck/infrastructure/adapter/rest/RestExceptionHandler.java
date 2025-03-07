package mrkwjck.infrastructure.adapter.rest;

import lombok.extern.slf4j.Slf4j;
import mrkwjck.domain.account.exception.AccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler({AccountNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleNotFoundException(AccountNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ErrorResponse(
                "ACCOUNT_NOT_FOUND", "Account not found by %s".formatted(exception.getAccountNumber()));
    }
}
