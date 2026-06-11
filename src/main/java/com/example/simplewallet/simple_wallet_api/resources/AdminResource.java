package com.example.simplewallet.simple_wallet_api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplewallet.simple_wallet_api.services.UserService;

@RestController
@RequestMapping("/admins/users")
public class AdminResource {

    @Autowired
    private UserService userService;

  
    @PutMapping("/{id}/lock")
    public ResponseEntity<Void> lockAccount(
            @PathVariable Long id, 
            @RequestHeader("admin-id") Long adminId) {

     
        userService.lockAccount(adminId, id);
        
        
        return ResponseEntity.noContent().build();
    }

   
    @PutMapping("/{id}/unlock")
    public ResponseEntity<Void> unlockAccount(
            @PathVariable Long id, 
            @RequestHeader("admin-id") Long adminId) {

        userService.unlockAccount(adminId, id);
        
        return ResponseEntity.noContent().build();
    }
}