package com.example.simplewallet.simple_wallet_api.resources;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplewallet.simple_wallet_api.dtos.MovementDTO;
import com.example.simplewallet.simple_wallet_api.services.WalletService;

@RestController
@RequestMapping("/wallets")
public class WalletResource {

    @Autowired
    private WalletService walletService;

    @PutMapping("{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestBody MovementDTO deposit) {

        walletService.deposit(id, deposit.amount());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestBody MovementDTO withdraw) {

        walletService.withdraw(id, withdraw.amount());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/balance")
    public ResponseEntity<BigDecimal> withdraw(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.getBalance(id));
    }

}
