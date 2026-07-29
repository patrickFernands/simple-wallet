package com.example.simplewallet.simple_wallet_api.resources;

import com.example.simplewallet.simple_wallet_api.dtos.TransactionResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplewallet.simple_wallet_api.dtos.MovementDTO;
import com.example.simplewallet.simple_wallet_api.dtos.TransactionDTO;
import com.example.simplewallet.simple_wallet_api.entities.Transaction;
import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.services.TransactionService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/{id}/transfer")
    public ResponseEntity<TransactionResponseDTO> transfer(@PathVariable Long id,
            @RequestBody TransactionDTO transaction) {

        Transaction newTransaction = transactionService.transfer(id, transaction.payeeId(), transaction.amount());

        User payee = newTransaction.getPayee();

        TransactionResponseDTO response = new TransactionResponseDTO(payee.getName(), newTransaction.getAmount(),
                newTransaction.getMoment());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
