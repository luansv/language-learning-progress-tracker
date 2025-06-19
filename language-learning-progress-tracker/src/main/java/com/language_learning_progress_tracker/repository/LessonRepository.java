package com.language_learning_progress_tracker.repository;

import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByUserId(Long userId);
    List<Lesson> findByLanguage(Language language);

}
