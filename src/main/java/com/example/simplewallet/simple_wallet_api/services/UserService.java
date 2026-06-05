package com.example.simplewallet.simple_wallet_api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.entities.Wallet;
import com.example.simplewallet.simple_wallet_api.repositories.UserRepository;

@Service
public class UserService {

	private final WalletService walletService;

	@Autowired
	private UserRepository repository;

	UserService(WalletService walletService) {
		this.walletService = walletService;
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		// exception aq no futuro pra caso não ache o user
		Optional<User> obj = repository.findById(id);
		return obj.get();
	}

	public void register(User user) {
		repository.save(user);
		Wallet newWallet = new Wallet(user, new BigDecimal("0.00"));
		walletService.saveWallet(newWallet);
	}

	public void changeAccountStatus(User admin, User user, Boolean lockedOrNot) {
		// exception aqui pra caso admin seja invalido
		User adminUser = findById(admin.getId());

		User userToChange = findById(user.getId());

		userToChange.setLockedAccount(lockedOrNot);

		repository.save(userToChange);
	}

}
