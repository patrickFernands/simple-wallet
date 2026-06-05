package com.example.simplewallet.simple_wallet_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simplewallet.simple_wallet_api.entities.Transaction;
import com.example.simplewallet.simple_wallet_api.repositories.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository repository;

	public List<Transaction> findAll() {
		return repository.findAll();
	}

	public Transaction findById(Long id) {
		Optional<Transaction> obj = repository.findById(id);
		return obj.get();
	}

}
