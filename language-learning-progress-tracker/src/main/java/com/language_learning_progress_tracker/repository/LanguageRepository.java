package com.language_learning_progress_tracker.repository;

import com.language_learning_progress_tracker.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository <Language, Long> {
    Optional<Language> findByName(String name);
    List<Language> findByNameIn(List<String> languages);
}
