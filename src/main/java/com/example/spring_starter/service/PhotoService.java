package com.example.spring_starter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_starter.model.Photo;
import com.example.spring_starter.repository.PhotoRepository;

@Service
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepo;

    public Photo save(Photo photo){
        return photoRepo.save(photo);
    }
    public List<Photo> findAll(){
        return photoRepo.findAll();
    }
    public Optional<Photo> findById(long id){
        return photoRepo.findById(id);
    }


}
