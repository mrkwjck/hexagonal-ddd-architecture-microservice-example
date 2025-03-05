package mrkwjck.application.port.in;

import org.iban4j.Iban;

public record AccountTransactionsQuery(Iban accountNumber) {}
