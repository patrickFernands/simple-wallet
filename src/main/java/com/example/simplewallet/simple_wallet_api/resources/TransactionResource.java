package com.example.simplewallet.simple_wallet_api.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplewallet.simple_wallet_api.entities.Transaction;
import com.example.simplewallet.simple_wallet_api.services.TransactionService;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionResource {

	@Autowired
	private TransactionService service;

	@GetMapping
	public ResponseEntity<List<Transaction>> findAll() {
		List<Transaction> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Transaction> findById(@PathVariable Long id) {
		Transaction obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
