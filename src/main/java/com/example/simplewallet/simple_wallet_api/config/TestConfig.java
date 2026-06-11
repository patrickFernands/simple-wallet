package com.example.simplewallet.simple_wallet_api.config;

import java.math.BigDecimal;

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

		User user1 = new User("João", "jose@gmail.com", "1234", Roles.USER);
		User user2 = new User("Cuca", "cuca@gmail.com", "4567", Roles.USER);
		User adm = new User("Maju", "maju@gmail.com", "0001", Roles.ADMIN);
		User seller = new User("Faria", "faria@gmail.com", "9000", Roles.SELLER);

		userService.register(user1);
		userService.register(user2);
		userService.register(adm);
		userService.register(seller);

		walletService.deposit(user1.getId(), new BigDecimal(100));

		walletService.withdraw(user1.getId(), new BigDecimal(50));

		userService.lockAccount(adm.getId(), user2.getId());

		transactionService.transfer(user1.getId(), user2.getId(), new BigDecimal(9));

		System.out.println(walletService.getBalance(user1.getId()));

	}

}
