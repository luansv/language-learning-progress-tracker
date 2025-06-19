package com.language_learning_progress_tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
