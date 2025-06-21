package com.language_learning_progress_tracker.service;

import com.language_learning_progress_tracker.dto.UserDto;
import com.language_learning_progress_tracker.dto.UserResponse;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.exception.UserNotFoundException;
import com.language_learning_progress_tracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserDto createUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        User newUser = userRepository.save(user);

        UserDto userDtoResponse = new UserDto();
        userDtoResponse.setId(newUser.getId());
        userDtoResponse.setUsername(newUser.getUsername());
        return userDtoResponse;
    }

    public UserResponse getAllUsers(int pageNo, int pageSize){
        Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll((org.springframework.data.domain.Pageable) pageable);
        List<User> listOfUsers = users.getContent();
        List<UserDto> content = listOfUsers.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setPageNo(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLast(users.isLast());

        return userResponse;

    }

    public UserDto getUserByUsername(String username){
        User byUsername = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Username is not associated to any user"));
        return mapToDto(byUsername);
    }

    public void deleteUserById(Long id){
        User delete = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User could not be deleted"));
        userRepository.delete(delete);
    }

    public UserDto getUserById(Long id){
        User byId = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User could not be found"));
        return mapToDto(byId);
    }

    public UserDto updateUser(UserDto userDto, Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User could not be updated"));

        user.setUsername(userDto.getUsername());

        User updatedUser = userRepository.save(user);
        return mapToDto(user);
    }

    private UserDto mapToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        return userDto;
    }


    private User mapToEntity(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        return user;
    }

}
