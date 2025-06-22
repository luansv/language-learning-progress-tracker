package com.language_learning_progress_tracker.service;

import com.language_learning_progress_tracker.dto.LessonDto;
import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.Lesson;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.exception.LanguageNotFoundException;
import com.language_learning_progress_tracker.exception.LessonNotFoundException;
import com.language_learning_progress_tracker.exception.UserNotFoundException;
import com.language_learning_progress_tracker.repository.LanguageRepository;
import com.language_learning_progress_tracker.repository.LessonRepository;
import com.language_learning_progress_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {
    private LessonRepository lessonRepository;
    private UserRepository userRepository;
    private LanguageRepository languageRepository;

    public LessonDto createLesson(Long userId, LessonDto lessonDto){
        Lesson lesson = mapToEntity(lessonDto);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated lesson not found"));
        lesson.setUser(user);
        Lesson newLesson = lessonRepository.save(lesson);
        return mapToDto(newLesson);
    }

    public List<LessonDto> getLessonByUserId(Long id){
        List<Lesson> lessons = lessonRepository.findByUserId(id);

        return lessons.stream().map(lesson -> mapToDto(lesson)).collect(Collectors.toList());
    }

    public List<LessonDto> getLessonByLanguage(Long userid, String language){

        User user = userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException("User not found with current ID"));

        Language language1 = languageRepository.findByName(language).orElseThrow(() -> new LanguageNotFoundException("Language not found!"));

        List<Lesson> lessons = lessonRepository.findByUserAndLanguage(user, language1);
        return lessons.stream().map(this::mapToDto).collect(Collectors.toList());    }

    public LessonDto getLessonById(Long lessonId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated lesson not found"));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new LessonNotFoundException("Lesson with associate user not found"));

        if (lesson.getUser().getId() != user.getId()){
            throw new LessonNotFoundException("This lesson does not belong to a user");
        }

        return mapToDto(lesson);
    }


    public LessonDto updateLesson(Long userId, Long lessonId, LessonDto lessonDto){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated lesson not found"));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new LessonNotFoundException("Lesson with associate user not found"));

        if (lesson.getUser().getId() != user.getId()){
            throw new LessonNotFoundException("This lesson does not belong to a user");
        }

        lesson.setTitle(lessonDto.getTitle());
        lesson.setLanguage(lessonDto.getLanguage());
        lesson.setDescription(lessonDto.getDescription());
        Lesson updatedLesson = lessonRepository.save(lesson);
        return mapToDto(updatedLesson);
    }

    public void deleteLesson(Long userId, Long lessonId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated lesson not found"));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new LessonNotFoundException("Lesson with associate user not found"));

        if (lesson.getUser().getId() != user.getId()){
            throw new LessonNotFoundException("This lesson does not belong to a user");
        }

        lessonRepository.delete(lesson);
    }


    private LessonDto mapToDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setTitle(lesson.getTitle());
        lessonDto.setLanguage(lesson.getLanguage());
        lessonDto.setDescription(lesson.getDescription());
        return lessonDto;
    }

    private Lesson mapToEntity(LessonDto lessonDto){
        Lesson lesson = new Lesson();
        lesson.setId(lessonDto.getId());
        lesson.setTitle(lessonDto.getTitle());
        lesson.setLanguage(lessonDto.getLanguage());
        lesson.setDescription(lessonDto.getDescription());
        return lesson;
    }
}
