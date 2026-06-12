package com.example.simplewallet.simple_wallet_api.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplewallet.simple_wallet_api.dtos.AuthenticationDTO;
import com.example.simplewallet.simple_wallet_api.dtos.AuthenticationResponseDTO;
import com.example.simplewallet.simple_wallet_api.dtos.UserRegisterDTO;
import com.example.simplewallet.simple_wallet_api.dtos.UserResponseDTO;
import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.infra.security.TokenService;
import com.example.simplewallet.simple_wallet_api.services.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var userNamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = this.authenticationManager.authenticate(userNamePassword);

		var token = tokenService.generateToken((User) (auth.getPrincipal()));

		return ResponseEntity.ok(new AuthenticationResponseDTO(token));
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO user) {

		String encryptedPassword = passwordEncoder.encode(user.password());

		User newUser = new User(user.name(), user.email(), encryptedPassword, user.role());

		User savedUser = userService.register(newUser);

		UserResponseDTO response = new UserResponseDTO(savedUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
