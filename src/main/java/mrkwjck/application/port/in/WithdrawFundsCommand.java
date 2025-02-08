package mrkwjck.application.port.in;

import java.math.BigDecimal;

import org.iban4j.Iban;


public record WithdrawFundsCommand(Iban accountNumber, BigDecimal amount) {
}
