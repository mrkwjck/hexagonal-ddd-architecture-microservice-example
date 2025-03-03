package mrkwjck.infrastructure.adapter.rest;

import java.math.BigDecimal;

record TransferFundsRequest(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount) {}
