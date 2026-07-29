package com.example.simplewallet.simple_wallet_api.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.entities.Wallet;
import com.example.simplewallet.simple_wallet_api.exceptions.DomainException;
import com.example.simplewallet.simple_wallet_api.repositories.WalletRepository;

import jakarta.transaction.Transactional;

@Service
public class WalletService {

	@Autowired
	private WalletRepository repository;

	public List<Wallet> findAll() {
		return repository.findAll();
	}

	public Wallet findById(Long id) {

		Wallet obj = repository.findById(id).orElseThrow(() -> new DomainException("Wallet not found!"));

		return obj;
	}

	public BigDecimal getBalance(Long id) {

		Wallet userWallet = repository.findByUserId(id).orElseThrow(() -> new DomainException("Wallet not found!"));

		return userWallet.getBalance();
	}

	public void saveWallet(Wallet wallet) {
		repository.save(wallet);
	}

	@Transactional
	public void deposit(Long id, BigDecimal amount) {

		Wallet wallet = repository.findByUserId(id).orElseThrow(() -> new DomainException("Wallet not found!"));

		User owner = wallet.getUser();

		if (owner.getIsLocked()) {
			throw new DomainException("Locked accounts can't receive deposits");
		}

		wallet.addBalance(amount);
		saveWallet(wallet);
	}

	@Transactional
	public void withdraw(Long id, BigDecimal amount) {
		Wallet wallet = repository.findByUserId(id).orElseThrow(() -> new DomainException("Wallet not found!"));

		User owner = wallet.getUser();

		if (owner.getIsLocked()) {
			throw new DomainException("Locked accounts can't do withdrawals");
		}

		wallet.subtractBalance(amount);
		saveWallet(wallet);
	}

}
