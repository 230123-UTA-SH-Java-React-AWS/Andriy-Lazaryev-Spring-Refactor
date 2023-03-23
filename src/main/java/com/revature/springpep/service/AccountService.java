package com.revature.springpep.service;

import com.revature.springpep.exceptions.AccountException;
import com.revature.springpep.model.Account;
import com.revature.springpep.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account register(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new AccountException("Username cannot be empty");
        }

        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new AccountException("Password must be at least 4 characters long");
        }

        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new AccountException("An account with this username already exists");
        }

        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AccountException("Username not found"));

        if (!account.getPassword().equals(password)) {
            throw new AccountException("Incorrect password");
        }

        return account;
    }

}
