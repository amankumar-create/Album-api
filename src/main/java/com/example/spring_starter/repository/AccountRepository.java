package com.example.spring_starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_starter.model.Account;
 
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    Optional<Account> findByEmail(String email);
}