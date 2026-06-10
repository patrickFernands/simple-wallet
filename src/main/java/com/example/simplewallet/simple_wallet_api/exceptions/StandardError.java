package com.example.simplewallet.simple_wallet_api.exceptions;

import java.time.Instant;

public record StandardError(
		Instant timestamp,
		Integer status,
		String error,
		String message,
		String path) {
}