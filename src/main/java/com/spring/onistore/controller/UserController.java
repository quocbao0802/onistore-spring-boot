package com.spring.onistore.controller;

import java.util.HashMap;

import javax.validation.Valid;

import com.spring.onistore.dto.SignInDto;
import com.spring.onistore.dto.SignUpDto;
import com.spring.onistore.entity.User;
import com.spring.onistore.repository.UserRepository;
import com.spring.onistore.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("api")
// @CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto signUpDto) {

        User newUser = new User();

        newUser.setEmail(signUpDto.getEmail());
        newUser.setUserName(signUpDto.getUserName());
        newUser.setAdmin(false);
        newUser.setEncryptedPassword(encoder().encode(signUpDto.getPassword()));

        userRepository.save(newUser);

        return new ResponseEntity<User>(newUser, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> postMethodName(@RequestBody SignInDto signInDto) {
        // TODO: process POST request
        HashMap<String, String> message = new HashMap<>();
        User foundUser = userRepository.findByUserName(signInDto.getUsername());
        if (foundUser == null) {
            message.put("message", "User not found");
            return new ResponseEntity<>(message, HttpStatus.valueOf(401));
        }

        if (!encoder().matches(signInDto.getPassword(), foundUser.getEncryptedPassword())) {
            message.put("message", "Wrong email or password");
            return new ResponseEntity<>(message, HttpStatus.valueOf(401));
        }
        JwtUtil jwtUtil = new JwtUtil();

        String token = jwtUtil.generateToken(foundUser);

        message.put("token", token);

        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/admin/auth")
    public ResponseEntity<?> auth() {
        HashMap<String, String> message = new HashMap<>();
        message.put("message", "Authenticated");
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/auth")
    public ResponseEntity<?> roles() {
        HashMap<String, String> message = new HashMap<>();
        message.put("message", "Authorize");
        return ResponseEntity.ok().body(message);
    }

}
