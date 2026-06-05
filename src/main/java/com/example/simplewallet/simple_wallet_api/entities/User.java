package com.example.simplewallet.simple_wallet_api.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.simplewallet.simple_wallet_api.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Email
	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Roles role;

	@Column(nullable = false)
	private Integer failedLoginAttempts;

	@Column(nullable = false)
	private Boolean lockedAccount;

	// pagt feitos
	@JsonIgnore
	@OneToMany(mappedBy = "payer")
	private List<Transaction> paymentsMade = new ArrayList<>();

	// pagt recebido
	@JsonIgnore
	@OneToMany(mappedBy = "payee")
	private List<Transaction> paymentsReceived = new ArrayList<>();

	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private Wallet wallet;

	public User() {
	}

	public User(String name, @Email String email, String password, Roles role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		lockedAccount = false;
		failedLoginAttempts = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getLockedAccount() {
		return lockedAccount;
	}

	public void setLockedAccount(Boolean lockedAccount) {
		this.lockedAccount = lockedAccount;
	}

	public Integer getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void addFailedLogin() {
		failedLoginAttempts++;
	}

	public List<Transaction> getPaymentsMade() {
		return paymentsMade;
	}

	public List<Transaction> getPaymentsReceived() {
		return paymentsReceived;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public Long getId() {
		return id;
	}

	public Roles getRole() {
		return role;
	}

	public Wallet getWallet() {
		return wallet;
	}

}
