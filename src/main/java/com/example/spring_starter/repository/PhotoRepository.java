package com.example.spring_starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_starter.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long>{

    
}  