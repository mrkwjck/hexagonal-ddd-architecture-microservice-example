package mrkwjck.application.port.in;

import java.math.BigDecimal;
import org.iban4j.Iban;

public record TransferFundsCommand(Iban sourceAccountIban, Iban targetAccountIban, BigDecimal amount) {}
