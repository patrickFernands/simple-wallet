package com.example.simplewallet.simple_wallet_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.enums.Roles;
import com.example.simplewallet.simple_wallet_api.repositories.UserRepository;
import com.example.simplewallet.simple_wallet_api.repositories.WalletRepository;
import com.example.simplewallet.simple_wallet_api.services.TransactionService;
import com.example.simplewallet.simple_wallet_api.services.UserService;
import com.example.simplewallet.simple_wallet_api.services.WalletService;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	private final UserService userService;
	private final WalletService walletService;
	private final TransactionService transactionService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WalletRepository walletRepository;

	TestConfig(WalletService walletService, UserService userService, TransactionService transactionService) {
		this.walletService = walletService;
		this.userService = userService;
		this.transactionService = transactionService;
	}

	@Override
	public void run(String... args) throws Exception {

		userService.register(new User("Joao", "jose@gmail.com", "1234", Roles.USER));
		userService.register(new User("Cuca", "cuca@gmail.com", "4567", Roles.USER));
		userService.register(new User("Maju", "maju@gmail.com", "0001", Roles.ADMIN));
		userService.register(new User("Faria", "faria@gmail.com", "9000", Roles.SELLER));

	}

}
