package com.example.simplewallet.simple_wallet_api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.enums.Roles;
import com.example.simplewallet.simple_wallet_api.repositories.TransactionRepository;
import com.example.simplewallet.simple_wallet_api.repositories.UserRepository;
import com.example.simplewallet.simple_wallet_api.repositories.WalletRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public void run(String... args) throws Exception {
		User u1 = new User("Maria", "maria@gmail.com", "9999", Roles.USER);
		User u2 = new User("Alex", "alex123@gmail.com", "1111", Roles.ADMIN);
		User u3 = new User("Obama", "obama@gmail.com", "5555", Roles.SELLER);

		userRepository.saveAll(Arrays.asList(u1, u2, u3));
	}

}
