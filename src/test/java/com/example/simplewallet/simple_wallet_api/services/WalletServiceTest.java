package com.example.simplewallet.simple_wallet_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.entities.Wallet;
import com.example.simplewallet.simple_wallet_api.enums.Roles;
import com.example.simplewallet.simple_wallet_api.exceptions.DomainException;
import com.example.simplewallet.simple_wallet_api.repositories.WalletRepository;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

	@Mock
	private WalletRepository repository;

	@InjectMocks
	private WalletService walletService;

	@Test
	void deveDepositarQuandoContaNaoEstaBloqueada() {
		User user = new User("Patrick", "patrick@email.com", "123", Roles.USER);
		Wallet wallet = new Wallet(user);

		when(repository.findByUserId(1L)).thenReturn(Optional.of(wallet));

		walletService.deposit(1L, new BigDecimal("100.00"));

		assertEquals(new BigDecimal("100.00"), wallet.getBalance());
		verify(repository, times(1)).save(wallet);
	}

	@Test
	void naoDeveDepositarQuandoContaEstaBloqueada() {
		User user = new User("Patrick", "patrick@email.com", "123", Roles.USER);
		user.lockAccount();
		Wallet wallet = new Wallet(user);

		when(repository.findByUserId(1L)).thenReturn(Optional.of(wallet));

		assertThrows(DomainException.class, () -> walletService.deposit(1L, new BigDecimal("100.00")));
	}

	@Test
	void deveSacarQuandoSaldoSuficiente() {
		User user = new User("Patrick", "patrick@email.com", "123", Roles.USER);
		Wallet wallet = new Wallet(user);
		wallet.addBalance(new BigDecimal("100.00"));

		when(repository.findByUserId(1L)).thenReturn(Optional.of(wallet));

		walletService.withdraw(1L, new BigDecimal("40.00"));

		assertEquals(new BigDecimal("60.00"), wallet.getBalance());
	}

	@Test
	void naoDeveSacarQuandoSaldoInsuficiente() {
		User user = new User("Patrick", "patrick@email.com", "123", Roles.USER);
		Wallet wallet = new Wallet(user);
		wallet.addBalance(new BigDecimal("10.00"));

		when(repository.findByUserId(1L)).thenReturn(Optional.of(wallet));

		assertThrows(DomainException.class, () -> walletService.withdraw(1L, new BigDecimal("50.00")));
	}

	@Test
	void deveLancarExcecaoQuandoCarteiraNaoEncontrada() {
		when(repository.findByUserId(99L)).thenReturn(Optional.empty());

		assertThrows(DomainException.class, () -> walletService.getBalance(99L));
	}
}