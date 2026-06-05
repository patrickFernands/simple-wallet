package com.example.simplewallet.simple_wallet_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simplewallet.simple_wallet_api.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
