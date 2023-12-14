package com.example.spring_starter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring_starter.model.Account;
import com.example.spring_starter.repository.AccountRepository;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepo.save(account);
    }

    public List<Account> findAll() {
        return accountRepo.findAll();
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepo.findByEmail(email);
    }

    public Optional<Account> findById(Long id) {
        return accountRepo.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepo.findByEmail(email);
        if (!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("Account not found");
        }
        Account account = optionalAccount.get();
        List<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(new SimpleGrantedAuthority(account.getEmail()));
        return new User(account.getEmail(), account.getPassword(), grantedAuthority);

    }
}
