package mrkwjck.domain.transaction.model;

public record TransactionId(Long value) {

    public TransactionId() {
        this(null);
    }

}
