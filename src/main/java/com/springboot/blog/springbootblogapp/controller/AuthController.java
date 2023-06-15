package com.springboot.blog.springbootblogapp.controller;

import com.springboot.blog.springbootblogapp.payload.JwtAuthResponse;
import com.springboot.blog.springbootblogapp.payload.LoginDto;
import com.springboot.blog.springbootblogapp.payload.SignupDto;
import com.springboot.blog.springbootblogapp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Build login Rest API

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse= new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    //build signup API

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody SignupDto signupDto){
        String response = authService.register(signupDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
