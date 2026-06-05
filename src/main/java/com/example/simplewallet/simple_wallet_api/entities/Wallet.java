package com.example.simplewallet.simple_wallet_api.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_wallet")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private BigDecimal balance;

	public Wallet() {
	}

	public Wallet(User user, BigDecimal balance) {
		this.user = user;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void addBalance(BigDecimal value) {
		balance = balance.add(value);
	}

	public void subtractBalance(BigDecimal value) {
		balance = balance.subtract(value);
	}

}
