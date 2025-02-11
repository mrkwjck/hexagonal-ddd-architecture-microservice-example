package mrkwjck.domain.transaction.model;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record TransactionId(Long value) {

    public TransactionId() {
        this(null);
    }

}
