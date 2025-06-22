package com.language_learning_progress_tracker.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

public class LanguageDto {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
