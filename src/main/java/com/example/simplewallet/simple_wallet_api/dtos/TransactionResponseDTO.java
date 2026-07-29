package com.example.simplewallet.simple_wallet_api.dtos;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponseDTO(String payeeName, BigDecimal amount, Instant time) {

}
