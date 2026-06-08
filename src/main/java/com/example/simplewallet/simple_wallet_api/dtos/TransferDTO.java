package com.example.simplewallet.simple_wallet_api.dtos;

import java.math.BigDecimal;

public record TransferDTO(Long payerId, Long payeeId, BigDecimal amount) {

}
