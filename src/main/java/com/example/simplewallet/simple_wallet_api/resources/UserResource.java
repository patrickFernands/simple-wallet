package com.example.simplewallet.simple_wallet_api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplewallet.simple_wallet_api.dtos.UserRegisterDTO;
import com.example.simplewallet.simple_wallet_api.dtos.UserResponseDTO;
import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO user) {

        User newUser = new User(user.name(),user.email(),user.password(),user.role());

        User savedUser = userService.register(newUser);

        UserResponseDTO response = new UserResponseDTO(savedUser);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    

}
