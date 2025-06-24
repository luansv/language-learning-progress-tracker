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

    public LessonService(LessonRepository lessonRepository, UserRepository userRepository, LanguageRepository languageRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
    }

    public LessonDto createLesson(Long userId, LessonDto lessonDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with associated lesson not found"));

        Lesson lesson = mapToEntity(lessonDto, user);
        Lesson saved = lessonRepository.save(lesson);
        return mapToDto(saved);

    }

    public List<LessonDto> getLessonByUserId(Long userid){
        List<Lesson> lessons = lessonRepository.findByUserId(userid);
        return lessons.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<LessonDto> getLessonByLanguage(Long userid, List<String> languages){

        User user = userRepository.findById(userid)
                .orElseThrow(() -> new UserNotFoundException("User not found with current ID"));

        List<Language> languageList = languageRepository.findByNameIn(languages);

        if (languageList.isEmpty()){
            throw new LanguageNotFoundException("No matching language found");
        }
        List<Lesson> lessons = lessonRepository.findByUserAndLanguagesIn(user, languageList);
        return lessons.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public LessonDto getLessonById(Long lessonId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found"));

        if (!lesson.getUser().getId().equals(user.getId())){
            throw new LessonNotFoundException("This lesson does not belong to the user");
        }

        return mapToDto(lesson);
    }


    public LessonDto updateLesson(Long userId, Long lessonId, LessonDto lessonDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found"));

        if (!lesson.getUser().getId().equals(user.getId())) {
            throw new LessonNotFoundException("This lesson does not belong to the user");
        }

        List<Language> languages = languageRepository.findByNameIn(lessonDto.getLanguages());

        if( languages.size() != lessonDto.getLanguages().size()){
            throw new LanguageNotFoundException("Some languages not found");
        }

        lesson.setTitle(lessonDto.getTitle());
        lesson.setDescription(lessonDto.getDescription());
        lesson.setLanguages(languages);

        Lesson updatedLesson = lessonRepository.save(lesson);
        return mapToDto(updatedLesson);
    }

    public void deleteLesson(Long userId, Long lessonId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found"));

        if (!lesson.getUser().getId().equals(user.getId())) {
            throw new LessonNotFoundException("This lesson does not belong to the user");
        }

        lessonRepository.delete(lesson);
    }


    private LessonDto mapToDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setTitle(lesson.getTitle());
        lessonDto.setDescription(lesson.getDescription());
        List<String> languageNames = lesson.getLanguages().stream()
                .map(Language::getName)
                .collect(Collectors.toList());

        lessonDto.setLanguages(languageNames);
        return lessonDto;
    }

    private Lesson mapToEntity(LessonDto lessonDto, User user) {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDto.getTitle());
        lesson.setDescription(lessonDto.getDescription());
        lesson.setUser(user);

        List<Language> languageEntities = languageRepository.findByNameIn(lessonDto.getLanguages());

        if (languageEntities.size() != lessonDto.getLanguages().size()) {
            throw new RuntimeException("Some languages were not found in the database");
        }

        lesson.setLanguages(languageEntities);

        return lesson;
    }

}
