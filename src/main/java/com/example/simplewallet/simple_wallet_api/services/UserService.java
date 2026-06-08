package com.example.simplewallet.simple_wallet_api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

		Optional<User> obj = repository.findById(id);

		if (obj.isEmpty()) {
			throw new DomainException("User not found");
		}

		return obj.get();
	}

	public User register(User user) {
		User savedUser = repository.save(user);
		Wallet newWallet = new Wallet(savedUser, new BigDecimal("0.00"));
		walletService.saveWallet(newWallet);
		return savedUser;
	}

	public User changeAccountStatus(Long adminId, Long userId, Boolean lockedOrNot) {

		User adminUser = findById(adminId);

		if (adminUser.getRole() != Roles.ADMIN) {
			throw new DomainException("Only admins can block or unlock accounts");
		}

		User userToChange = findById(userId);
		userToChange.setLockedAccount(lockedOrNot);

		repository.save(userToChange);

		return userToChange;
}

}
