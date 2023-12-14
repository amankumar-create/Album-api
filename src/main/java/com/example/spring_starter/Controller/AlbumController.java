package com.example.spring_starter.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.spring_starter.model.Account;
import com.example.spring_starter.model.Album;
import com.example.spring_starter.payload.album.AlbumDto;
import com.example.spring_starter.payload.album.AlbumViewDto;
import com.example.spring_starter.service.AccountService;
import com.example.spring_starter.service.AlbumService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AlbumViewDto>> getAlbums() {
        List<Album> albums = albumService.findAll();
        List<AlbumViewDto> albumViewDtos = new ArrayList<>();
        for (Album a : albums) {
            AlbumViewDto avd = new AlbumViewDto(a.getId(), a.getName(), a.getDescription());
            albumViewDtos.add(avd);
        }
        return new ResponseEntity<List<AlbumViewDto>>(albumViewDtos, HttpStatus.OK);
    }

    @GetMapping("/{account-id}")
    public ResponseEntity<List<AlbumViewDto>> getAlbumsByAccountId(@RequestParam Long accountId) {

        List<Album> albums = albumService.findByAccountId(accountId);
        List<AlbumViewDto> albumViewDtos = new ArrayList<>();
        for (Album a : albums) {
            AlbumViewDto avd = new AlbumViewDto(a.getId(), a.getName(), a.getDescription());
            albumViewDtos.add(avd);
        }
        return new ResponseEntity<List<AlbumViewDto>>(albumViewDtos, HttpStatus.OK);
    }

    @PostMapping("/album/add")
    @SecurityRequirement(name = "Secure.api")
    public ResponseEntity<AlbumViewDto> addAlbum(@Valid @RequestBody AlbumDto albumDto, Authentication authentication) {
        Album album = new Album();
        album.setName(albumDto.getName());
        album.setDescription(albumDto.getDescription());
        Optional<Account> optionalAccount = accountService.findByEmail(authentication.getName());
        album.setAccount(optionalAccount.get());
        albumService.save(album);
        AlbumViewDto albumViewDto = new AlbumViewDto(album.getId(), album.getName(), album.getDescription());
        return new ResponseEntity<>(albumViewDto, HttpStatus.CREATED);
    }

}
