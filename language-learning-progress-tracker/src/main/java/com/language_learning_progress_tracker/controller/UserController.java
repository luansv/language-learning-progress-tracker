package com.language_learning_progress_tracker.controller;

import com.language_learning_progress_tracker.dto.UserDto;
import com.language_learning_progress_tracker.dto.UserOverviewDto;
import com.language_learning_progress_tracker.dto.UserResponse;
import com.language_learning_progress_tracker.security.SecurityConfig;
import com.language_learning_progress_tracker.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}/overview")
    public ResponseEntity<UserOverviewDto> getUserOverview(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserOverview(userId));
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("user/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("user/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> userRegister(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping("user/{id}/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Long userId){
        UserDto response = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("user/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

}
