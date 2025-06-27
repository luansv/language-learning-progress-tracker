package com.language_learning_progress_tracker.service;

import com.language_learning_progress_tracker.dto.*;
import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.Lesson;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.entity.Vocabulary;
import com.language_learning_progress_tracker.exception.UserNotFoundException;
import com.language_learning_progress_tracker.repository.LessonRepository;
import com.language_learning_progress_tracker.repository.UserRepository;
import com.language_learning_progress_tracker.repository.VocabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final VocabRepository vocabRepository;

    @Autowired
    public UserService(UserRepository userRepository, LessonRepository lessonRepository, VocabRepository vocabRepository) {
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.vocabRepository = vocabRepository;
    }

    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        User newUser = userRepository.save(user);

        UserDto userDtoResponse = new UserDto();
        userDtoResponse.setId(newUser.getId());
        userDtoResponse.setUsername(newUser.getUsername());
        userDtoResponse.setPassword(newUser.getPassword());
        return userDtoResponse;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            return dto;
        }).collect(Collectors.toList());
    }

    public UserDto getUserByUsername(String username) {
        User byUsername = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Username is not associated to any user"));
        return mapToDto(byUsername);
    }

    public void deleteUserById(Long id) {
        User delete = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User could not be deleted"));
        userRepository.delete(delete);
    }

    public UserDto getUserById(Long id) {
        User byId = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User could not be found"));
        return mapToDto(byId);
    }

    public UserDto updateUser(UserDto userDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User could not be updated"));

        user.setUsername(userDto.getUsername());

        User updatedUser = userRepository.save(user);
        return mapToDto(user);
    }

    public UserOverviewDto getUserOverview(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Lesson> lessons = lessonRepository.findByUserId(userId);
        List<Vocabulary> vocabularies = vocabRepository.findByUserId(userId);

        Set<Language> allLanguages = new HashSet<>();
        for (Lesson lesson : lessons) {
            allLanguages.addAll(lesson.getLanguages());
        }
        for (Vocabulary vocab : vocabularies) {
            allLanguages.addAll(vocab.getLanguages());
        }

        UserOverviewDto dto = new UserOverviewDto();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setLessons(lessons.stream().map(this::mapLessonToDto).toList());
        dto.setVocabulary(vocabularies.stream().map(this::mapVocabToDto).toList());
        dto.setLanguages(allLanguages.stream().map(this::mapLanguageToDto).toList());

        return dto;
    }

    private LessonDto mapLessonToDto(Lesson lesson) {
        LessonDto dto = new LessonDto();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setDescription(lesson.getDescription());
        dto.setLanguages(lesson.getLanguages().stream()
                .map(Language::getName)
                .collect(Collectors.toList()));
        return dto;
    }

    private VocabDto mapVocabToDto(Vocabulary vocab) {
        VocabDto dto = new VocabDto();
        dto.setId(vocab.getId());
        dto.setWord(vocab.getWord());
        dto.setExample(vocab.getExample());
        dto.setMeaning(vocab.getMeaning());
        dto.setLanguages(vocab.getLanguages().stream()
                .map(Language::getName)
                .collect(Collectors.toList()));
        return dto;
    }

    private LanguageDto mapLanguageToDto(Language language) {
        LanguageDto dto = new LanguageDto();
        dto.setId(language.getId());
        dto.setName(language.getName());
        return dto;
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        return userDto;
    }


    private User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        return user;
    }

}
