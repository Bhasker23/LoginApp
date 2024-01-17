package com.springSecuirty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("End point is not secured with Spring Security", HttpStatus.OK);
    }

    @GetMapping("/welcomeWithSecurity")
    public ResponseEntity<String> welcomeWithSecurity(){
        return new ResponseEntity<>("End point is secured with Spring Security", HttpStatus.OK);
    }
    @PostMapping("/admin")
    public ResponseEntity<String> admin(){
        return new ResponseEntity<>("Welcome admin", HttpStatus.OK);
    }
}
