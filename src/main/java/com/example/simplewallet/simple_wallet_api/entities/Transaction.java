package com.example.simplewallet.simple_wallet_api.entities;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "payer_id", nullable = false)
	private User payer;

	@ManyToOne
	@JoinColumn(name = "payee_id", nullable = false)
	private User payee;

	@Column(nullable = false)
	private BigDecimal amount;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	@Column(nullable = false)
	private Instant moment;

	public Transaction() {
	}

	public Transaction(User payer, User payee, BigDecimal amount, Instant moment) {
		this.payer = payer;
		this.payee = payee;
		this.amount = amount;
		this.moment = moment;
	}

	public Long getId() {
		return id;
	}

	public String getPayer() {
		return payer.getName();
	}

	public String getPayee() {
		return payee.getName();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Instant getMoment() {
		return moment;
	}

}
