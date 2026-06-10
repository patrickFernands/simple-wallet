package com.example.simplewallet.simple_wallet_api.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simplewallet.simple_wallet_api.entities.Transaction;
import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.entities.Wallet;
import com.example.simplewallet.simple_wallet_api.enums.Roles;
import com.example.simplewallet.simple_wallet_api.exceptions.DomainException;
import com.example.simplewallet.simple_wallet_api.repositories.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

	private final WalletService walletService;

	private final UserService userService;

	@Autowired
	private TransactionRepository repository;

	TransactionService(UserService userService, WalletService walletService) {
		this.userService = userService;
		this.walletService = walletService;
	}

	public List<Transaction> findAll() {
		return repository.findAll();
	}

	public Transaction findById(Long id) {

		Optional<Transaction> obj = repository.findById(id);

		if (obj.isEmpty()) {
			throw new DomainException("Transaction not found!");
		}

		return obj.get();
	}

	@Transactional
	public void transfer(Long payerId, Long payeeId, BigDecimal amount) {
		User payer = userService.findById(payerId);
		User payee = userService.findById(payeeId);

		if (payer.getRole() == Roles.SELLER) {
			throw new DomainException("Legal Person accounts can't transfer");
		}

		Wallet payerWallet = walletService.findById(payer.getWallet());
		Wallet payeeWallet = walletService.findById(payee.getWallet());
		
		if (payerWallet.getBalance().compareTo(amount) < 0) {
			throw new DomainException("Account without balance");
		}

		payerWallet.subtractBalance(amount);
		payeeWallet.addBalance(amount);

		Transaction transaction = new Transaction(payer, payee, amount, Instant.now());
		repository.save(transaction);

		walletService.saveWallet(payerWallet);
		walletService.saveWallet(payeeWallet);

	}

}
