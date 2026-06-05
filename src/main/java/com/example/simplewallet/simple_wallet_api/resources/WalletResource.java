package com.example.simplewallet.simple_wallet_api.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplewallet.simple_wallet_api.entities.Wallet;
import com.example.simplewallet.simple_wallet_api.services.WalletService;

@RestController
@RequestMapping(value = "/wallets")
public class WalletResource {

	@Autowired
	private WalletService service;

	@GetMapping
	public ResponseEntity<List<Wallet>> findAll() {
		List<Wallet> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Wallet> findById(@PathVariable Long id) {
		Wallet obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
