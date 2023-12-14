package com.example.spring_starter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_starter.model.Album;
import com.example.spring_starter.repository.AlbumRepository;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepo;

    public Album save(Album album) {
        return albumRepo.save(album);
    }

    public List<Album> findAll() {
        return albumRepo.findAll();
    }

    public Optional<Album> findById(Long id) {
        return albumRepo.findById(id);
    }

    public List<Album> findByAccountId(Long id) {
        return albumRepo.findByAccount_id(id);
    }
}
