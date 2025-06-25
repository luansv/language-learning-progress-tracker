package com.language_learning_progress_tracker.dto;

import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.User;

import java.util.List;

public class VocabDto {
    private Long id;
    private String word;
    private String example;
    private String meaning;
    private List<String> languages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> language) {
        this.languages = language;
    }
}
