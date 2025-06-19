package com.language_learning_progress_tracker.repository;

import com.language_learning_progress_tracker.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository <Language, Long> {
}
