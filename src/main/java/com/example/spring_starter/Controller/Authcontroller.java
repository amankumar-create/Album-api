package com.example.spring_starter.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring_starter.payload.auth.TokenDto;
import com.example.spring_starter.payload.auth.UserLoginDto;
import com.example.spring_starter.service.TokenService;

@Controller
public class Authcontroller {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public Authcontroller(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    @ResponseBody
    public ResponseEntity<TokenDto> token(@RequestBody UserLoginDto userLogin) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()));
            return ResponseEntity.ok(new TokenDto(tokenService.generateToken(authentication)));
        } catch (Exception e) {
            return new ResponseEntity<>(new TokenDto(null), HttpStatus.BAD_REQUEST);
        }
    }

}
