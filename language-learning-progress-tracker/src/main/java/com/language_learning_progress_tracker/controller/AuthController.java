package com.language_learning_progress_tracker.controller;

import com.language_learning_progress_tracker.dto.AuthenticationDTO;
import com.language_learning_progress_tracker.dto.LoginResponseDto;
import com.language_learning_progress_tracker.dto.RegisterDto;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.entity.UserRole;
import com.language_learning_progress_tracker.repository.UserRepository;
import com.language_learning_progress_tracker.security.CustomUserDetails;
import com.language_learning_progress_tracker.security.TokenGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
       var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
       var auth = this.authenticationManager.authenticate(usernamePassword);

        var customUserDetails = (CustomUserDetails) auth.getPrincipal();
        User user = customUserDetails.getUser();

        var token = tokenGenerator.generateToken(user);

       return ResponseEntity.ok(new LoginResponseDto(token));
    }


    @PostMapping("register")
    public ResponseEntity registerUser(@RequestBody RegisterDto registerDto){
        if (userRepository.existsByUsername(registerDto.username())){
            return new ResponseEntity<>("Error!", HttpStatus.BAD_REQUEST);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        User newUser = new User(registerDto.username(), encryptedPassword, UserRole.USER);

        userRepository.save(newUser);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);

    }

}
