package com.revature.springpep.controller;

import com.revature.springpep.exceptions.AccountException;
import com.revature.springpep.exceptions.MessageException;
import com.revature.springpep.model.Account;
import com.revature.springpep.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ExceptionHandler({AccountException.class, MessageException.class})
    public ResponseEntity<Object> handleCustomExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Optional<Account> accountResponse = Optional.ofNullable(accountService.register(account));
        if (accountResponse.isPresent()) {
            return ResponseEntity.ok(accountResponse.get());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Optional<Account> accountResponse = Optional.ofNullable(accountService.login(account.getUsername(), account.getPassword()));
        if (accountResponse.isPresent()) {
            return ResponseEntity.ok(accountResponse.get());
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}