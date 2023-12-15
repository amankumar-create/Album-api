package com.example.spring_starter.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring_starter.model.Account;
import com.example.spring_starter.model.Album;
import com.example.spring_starter.model.Photo;
import com.example.spring_starter.payload.album.AlbumDto;
import com.example.spring_starter.payload.album.AlbumViewDto;
import com.example.spring_starter.service.AccountService;
import com.example.spring_starter.service.AlbumService;
import com.example.spring_starter.service.PhotoService;
import com.example.spring_starter.util.appUtils.AppUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AccountService accountService;

    @Autowired 
    private PhotoService photoService;

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

    @GetMapping(value="/{account-id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    
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

    @PostMapping(value = "/{album_id}/photos" , consumes =  {"multipart/form-data"}  )
    @Operation(summary = "Upload photos to album")
    @SecurityRequirement(name = "Secure.api")
    public ResponseEntity<List<String>> UploadFile(@PathVariable Long album_id, @RequestPart(required = true) MultipartFile[] files, Authentication authentication ){
        String email = authentication.getName();
        Optional<Account> accOptional = accountService.findByEmail(email);
        Account authAccount = accOptional.get();
        Optional<Album> albumOptional = albumService.findById(album_id);
        if(!albumOptional.isPresent()){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        
        Album album = albumOptional.get();
        if(album.getAccount().getId()!= authAccount.getId()){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

        List<String> fileNamesWithSuccess = new ArrayList<>();
        List<String> fileNamesWithError = new ArrayList<>();

        Arrays.asList(files).stream().forEach(file->{
            String contentType = file.getContentType();
            if(contentType.equals("image/png")
            ||contentType.equals("image/jpg")
            ||contentType.equals("image/jpeg")){
                fileNamesWithSuccess.add(file.getOriginalFilename());
                int length = 10;
                boolean useLetters = true;
                boolean useNumbers = true;

                try{
                    String fileName = file.getOriginalFilename();
                    String generatedPrefix = RandomStringUtils.random(length, useLetters, useNumbers);
                    String finalFileName = generatedPrefix + fileName;
                    String absolute_fileLocation = AppUtil.get_photo_upload_path(finalFileName, album_id);
                    Path path = Paths.get(absolute_fileLocation);
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    Photo photo = new Photo();
                    photo.setName(fileName);
                    photo.setFileName(finalFileName);
                    photo.setOriginalFileName(fileName);
                    photo.setAlbum(album);
                    photoService.save(photo);

                }catch(Exception e){
                    System.out.println("cant copy file");
                }
            }
            else{
                fileNamesWithError.add(file.getOriginalFilename());
            }
        });
        return new ResponseEntity<>(fileNamesWithSuccess, HttpStatus.OK );    
        
    }

}
