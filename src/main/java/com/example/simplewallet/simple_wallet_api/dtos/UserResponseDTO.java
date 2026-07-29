package com.example.simplewallet.simple_wallet_api.dtos;

import com.example.simplewallet.simple_wallet_api.entities.User;
import com.example.simplewallet.simple_wallet_api.enums.Roles;

public record UserResponseDTO(Long id, String name, String email, Roles role) {

    public UserResponseDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}