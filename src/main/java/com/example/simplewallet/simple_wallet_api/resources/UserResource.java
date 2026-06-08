package com.example.simplewallet.simple_wallet_api.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.simplewallet.simple_wallet_api.dtos.ChangeStatusDTO;
import com.example.simplewallet.simple_wallet_api.dtos.UserRegisterDTO;
import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<User> register(@RequestBody UserRegisterDTO obj) {

		String senha = obj.password();

		User userEntity = new User(obj.name(), obj.email(), senha, obj.role());

		User newUser = service.register(userEntity);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();

		return ResponseEntity.created(uri).body(newUser);

	}

	@PutMapping(value = "/{id}/status")
	public ResponseEntity<User> changeStatus(@PathVariable Long id, @RequestBody ChangeStatusDTO dto) {

		User changedUser = service.changeAccountStatus(dto.adminId(), id, dto.locked());

		return ResponseEntity.ok().body(changedUser);
	}

}
