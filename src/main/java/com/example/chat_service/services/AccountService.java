package com.example.chat_service.services;

import com.example.chat_service.model.account.Account;
import com.example.chat_service.repositoryes.account.AccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var currentAccount = accountRepository.findByEmail(username).orElseThrow(() -> new RuntimeException(""));
        return new User(currentAccount.getEmail(), currentAccount.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(currentAccount.getAuthorities())));
    }
}
