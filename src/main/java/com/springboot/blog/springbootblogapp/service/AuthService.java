package com.springboot.blog.springbootblogapp.service;

import com.springboot.blog.springbootblogapp.payload.LoginDto;
import com.springboot.blog.springbootblogapp.payload.SignupDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(SignupDto signupDto);
}
