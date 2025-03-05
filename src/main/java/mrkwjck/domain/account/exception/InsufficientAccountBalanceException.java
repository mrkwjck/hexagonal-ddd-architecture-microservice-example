package mrkwjck.domain.account.exception;

public class InsufficientAccountBalanceException extends RuntimeException {

    public InsufficientAccountBalanceException(String message) {
        super(message);
    }
}
