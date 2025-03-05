package mrkwjck.domain.account.event;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;
import mrkwjck.domain.DomainEvent;
import org.iban4j.Iban;

@Getter
@ToString
public class FundsWithdrawn extends DomainEvent {

    private final String accountNumber;
    private final BigDecimal amount;

    public FundsWithdrawn(Iban accountNumber, BigDecimal amount) {
        super("FUNDS_WITHDRAWN");
        this.accountNumber = accountNumber.toString();
        this.amount = amount;
    }
}
