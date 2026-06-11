package com.example.simplewallet.simple_wallet_api.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

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

		Transaction obj = repository.findById(id).orElseThrow(() -> new DomainException("Transaction not found!"));

		return obj;
	}

	@Transactional
	public Transaction transfer(Long payerId, Long payeeId, BigDecimal amount) {
		User payer = userService.findById(payerId);
		User payee = userService.findById(payeeId);

		if (payer.getIsLocked()) {
			throw new DomainException("Locked accounts can't transfer");
		}

		if (payee.getIsLocked()) {
			throw new DomainException("Locked accounts can't receive");
		}

		if (payer.getRole() == Roles.SELLER) {
			throw new DomainException("Legal Person accounts can't transfer");
		}

		Wallet payerWallet = payer.getWallet();
		Wallet payeeWallet = payee.getWallet();

		payerWallet.subtractBalance(amount);
		payeeWallet.addBalance(amount);

		Transaction transaction = new Transaction(payer, payee, amount, Instant.now());
		repository.save(transaction);

		walletService.saveWallet(payerWallet);
		walletService.saveWallet(payeeWallet);

		return transaction;
	}

}
