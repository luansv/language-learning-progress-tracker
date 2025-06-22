package com.language_learning_progress_tracker.dto;


import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.User;

import java.util.List;

public class LessonDto {
    private Long id;
    private String title;
    private String description;
    private List<String> languages;
    private Long userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }


}
