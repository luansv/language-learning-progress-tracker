package com.language_learning_progress_tracker.dto;

import java.util.List;


public class UserOverviewDto {
    private Long userId;
    private String username;
    private List<LessonDto> lessons;
    private List<VocabDto> vocabulary;
    private List<LanguageDto> languages;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDto> lessons) {
        this.lessons = lessons;
    }

    public List<VocabDto> getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(List<VocabDto> vocabulary) {
        this.vocabulary = vocabulary;
    }

    public List<LanguageDto> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageDto> languages) {
        this.languages = languages;
    }
}
