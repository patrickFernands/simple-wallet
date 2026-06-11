package com.example.simplewallet.simple_wallet_api.dtos;

import com.example.simplewallet.simple_wallet_api.enums.Roles;

public record UserRegisterDTO(String name, String email, String password, Roles role) {

}