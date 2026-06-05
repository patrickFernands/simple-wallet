package com.example.simplewallet.simple_wallet_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simplewallet.simple_wallet_api.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
