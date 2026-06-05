package com.example.simplewallet.simple_wallet_api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simplewallet.simple_wallet_api.entities.Wallet;
import com.example.simplewallet.simple_wallet_api.exceptions.DomainException;
import com.example.simplewallet.simple_wallet_api.repositories.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository repository;

	public List<Wallet> findAll() {
		return repository.findAll();
	}

	public Wallet findById(Long id) {

		Optional<Wallet> obj = repository.findById(id);

		if (obj.isEmpty()) {
			throw new DomainException("Wallet not found!");
		}

		return obj.get();
	}

	public BigDecimal getBalance(Long id) {
		return findById(id).getBalance();
	}

	public void saveWallet(Wallet wallet) {
		repository.save(wallet);
	}

}
