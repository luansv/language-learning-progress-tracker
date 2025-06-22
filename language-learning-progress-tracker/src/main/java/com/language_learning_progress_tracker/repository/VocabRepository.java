package com.language_learning_progress_tracker.repository;

import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VocabRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findByUser(User userId);
    List<Vocabulary> findByLanguage(Language language);
    List<Vocabulary> findByUserAndLanguage(User userId, Language language);
}
