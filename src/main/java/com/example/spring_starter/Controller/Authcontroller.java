package com.example.spring_starter.Controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring_starter.payload.auth.Token;
import com.example.spring_starter.payload.auth.UserLogin;
import com.example.spring_starter.service.TokenService;


@Controller
public class Authcontroller {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public Authcontroller(TokenService tokenService, AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    @ResponseBody
    public Token token(@RequestBody UserLogin userLogin) throws AuthenticationException{
        Authentication authentication  = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()));
        return new Token(tokenService.generateToken(authentication));
    }
    
}
