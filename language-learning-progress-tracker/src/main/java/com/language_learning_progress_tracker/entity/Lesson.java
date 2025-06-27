package com.language_learning_progress_tracker.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "lesson_language",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> languages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id") // OK
    private User user;

    public Lesson() {
    }

    public Lesson(Long id, String title, String description, List<Language> languages, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.languages = languages;
        this.user = user;
    }

    public Lesson(String title, String description, List<Language> languages, User user) {
        this.title = title;
        this.description = description;
        this.languages = languages;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
