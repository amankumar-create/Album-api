package com.example.spring_starter.Controller;

 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
 


@RestController
public class HomeController {

    @GetMapping("/home")
    @SecurityRequirement(name = "Secure.api")
    public String home ( Model model){
        return "Hello !";
    }
    
    @GetMapping("/test")
    @SecurityRequirement(name = "Secure.api")
    public String test ( Model model){
        return "testing";
    }
    @GetMapping("/public")
    public String getPublic(Model model) {
        return "Public";
    }
    
}
