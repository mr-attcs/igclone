package com.mrattcs.instagramapi.controller;

import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.User;
import com.mrattcs.instagramapi.repo.UserRepository;
import com.mrattcs.instagramapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {
        User createdUser = userService.registerUser(user);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }


    @GetMapping("/signin")
    public ResponseEntity<User> signInHandler(Authentication authentication) throws BadCredentialsException {
        Optional<User> oUser = userRepository.findByEmail(authentication.getName());
        if (oUser.isPresent()) {
            return new ResponseEntity<>(oUser.get(), HttpStatus.ACCEPTED);
        }
        throw new BadCredentialsException("Invalid username or password");

    }
}
