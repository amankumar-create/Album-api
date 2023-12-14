package com.example.spring_starter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.spring_starter.model.Account;
import com.example.spring_starter.service.AccountService;

@Component
public class SeedData implements CommandLineRunner{

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        
        Account account1 = new Account();
        account1.setEmail("aman64241@gmail.com");        
        account1.setPassword("123456");

        Account account2 = new Account();
        account2.setEmail("user404@gmail.com");        
        account2.setPassword("1234567");

        accountService.save(account1);        
        accountService.save(account2);

        
    }
    
}
