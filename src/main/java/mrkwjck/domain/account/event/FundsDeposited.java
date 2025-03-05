package mrkwjck.domain.account.event;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;
import mrkwjck.domain.DomainEvent;
import org.iban4j.Iban;

@Getter
@ToString
public class FundsDeposited extends DomainEvent {

    private final String accountNumber;
    private final BigDecimal amount;

    public FundsDeposited(Iban accountNumber, BigDecimal amount) {
        super("FUNDS_DEPOSITED");
        this.accountNumber = accountNumber.toString();
        this.amount = amount;
    }
}
