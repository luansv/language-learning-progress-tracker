package com.language_learning_progress_tracker.controller;

import com.language_learning_progress_tracker.dto.LessonDto;
import com.language_learning_progress_tracker.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class LessonController {
    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("user/{userId}/lessons")
    public ResponseEntity<LessonDto> createLesson (@PathVariable(value = "userId") Long userId,
                                                   @RequestBody LessonDto lessonDto){
        return new ResponseEntity<>(lessonService.createLesson(userId, lessonDto), HttpStatus.CREATED);
    }

    @GetMapping("user/{userId}/lessons/{lessonId}")
    public ResponseEntity<LessonDto> getLessonById(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "lessonId") Long lessonId)
    {

        LessonDto lessonByIdDto = lessonService.getLessonById(userId, lessonId);
        return new ResponseEntity<>(lessonByIdDto, HttpStatus.OK);
    }

    @GetMapping("user/{userId}/lessons")
    public ResponseEntity<List<LessonDto>> getLessonsByLanguages(
            @PathVariable Long userId,
            @RequestParam List<String> languages) {

        List<LessonDto> lessons = lessonService.getLessonByLanguage(userId, languages);
        return ResponseEntity.ok(lessons);
    }

    @PutMapping("user/{userId}/lessons/{lessonId}")
    public ResponseEntity<LessonDto> updateLesson(@PathVariable Long userId,
                                                  @PathVariable Long lessonId,
                                                  @RequestBody LessonDto lessonDto) {
        LessonDto updatedLesson = lessonService.updateLesson(userId, lessonId, lessonDto);
        return new ResponseEntity<>(updatedLesson, HttpStatus.OK);
    }

    @DeleteMapping("user/{userId}/lessons/{lessonId}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long userId,
                                               @PathVariable Long lessonId) {
        lessonService.deleteLesson(userId, lessonId);
        return new ResponseEntity<>("Lesson successfully deleted", HttpStatus.OK);
    }
}
