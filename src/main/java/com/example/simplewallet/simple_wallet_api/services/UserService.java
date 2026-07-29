package com.example.simplewallet.simple_wallet_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.entities.Wallet;
import com.example.simplewallet.simple_wallet_api.enums.Roles;
import com.example.simplewallet.simple_wallet_api.exceptions.DomainException;
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

		User obj = repository.findById(id).orElseThrow(() -> new DomainException("User not found!"));

		return obj;
	}

	public User register(User user) {

		if (!repository.findByEmail(user.getEmail()).isEmpty()) {
			throw new DomainException("This email is already in use");
		}

		User savedUser = repository.save(user);
		Wallet newWallet = new Wallet(savedUser);
		savedUser.setWallet(newWallet);
		walletService.saveWallet(newWallet);
		return savedUser;
	}

	public void lockAccount(Long adminId, Long userId) {

		User adminUser = findById(adminId);

		if (adminUser.getRole() != Roles.ADMIN) {
			throw new DomainException("Only admins can block accounts");
		}

		User userToChange = findById(userId);

		if (userToChange.getIsLocked()) {
			throw new DomainException("This account is already locked!");
		}

		userToChange.lockAccount();

		repository.save(userToChange);
	}

	public void unlockAccount(Long adminId, Long userId) {

		User adminUser = findById(adminId);

		if (adminUser.getRole() != Roles.ADMIN) {
			throw new DomainException("Only admins can unlock accounts");
		}

		User userToChange = findById(userId);

		if (!userToChange.getIsLocked()) {
			throw new DomainException("This account is already unlocked!");
		}
		userToChange.unlockAccount();

		repository.save(userToChange);
	}

}
