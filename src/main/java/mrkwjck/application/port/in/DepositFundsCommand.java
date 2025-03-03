package mrkwjck.application.port.in;

import java.math.BigDecimal;
import org.iban4j.Iban;

public record DepositFundsCommand(Iban accountNumber, BigDecimal amount) {}
