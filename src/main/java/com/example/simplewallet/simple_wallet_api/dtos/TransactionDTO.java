package com.example.simplewallet.simple_wallet_api.dtos;

import java.math.BigDecimal;

public record TransactionDTO(Long payeeId, BigDecimal amount) {

}
