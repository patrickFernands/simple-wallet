package com.example.simplewallet.simple_wallet_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simplewallet.simple_wallet_api.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
