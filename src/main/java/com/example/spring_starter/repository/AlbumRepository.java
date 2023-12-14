package com.example.spring_starter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_starter.model.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    public List<Album> findByAccount_id(Long id);
}