package com.springboot.blog.springbootblogapp.service.impl;

import com.springboot.blog.springbootblogapp.entity.Role;
import com.springboot.blog.springbootblogapp.entity.User;
import com.springboot.blog.springbootblogapp.exception.BlogAPIException;
import com.springboot.blog.springbootblogapp.payload.LoginDto;
import com.springboot.blog.springbootblogapp.payload.SignupDto;
import com.springboot.blog.springbootblogapp.repository.RoleRepository;
import com.springboot.blog.springbootblogapp.repository.UserRepository;
import com.springboot.blog.springbootblogapp.security.JwtTokenProvider;
import com.springboot.blog.springbootblogapp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(SignupDto signupDto) {

        //add check for username exist in database or not
        if(userRepository.existsByUsername(signupDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "User Already exists");
        }

        //add check for email in database
        if(userRepository.existsByEmail(signupDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email Already exists");
        }

        User user = new User();

        user.setEmail(signupDto.getEmail());
        user.setUsername(signupDto.getUsername());
        user.setName(signupDto.getName());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "User register done";
    }
}
