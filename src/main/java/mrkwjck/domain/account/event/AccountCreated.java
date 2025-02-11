package mrkwjck.domain.account.event;

import lombok.Getter;
import lombok.ToString;
import mrkwjck.domain.DomainEvent;
import org.iban4j.Iban;

@Getter
@ToString
public class AccountCreated extends DomainEvent {

    private final String accountNumber;

    public AccountCreated(Iban accountNumber) {
        super("ACCOUNT_CREATED");
        this.accountNumber = accountNumber.toString();
    }

}
