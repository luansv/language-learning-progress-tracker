package com.language_learning_progress_tracker.repository;

import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VocabRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findByUser(User userId);
    List<Vocabulary> findByLanguage(Language language);

    @Query("SELECT v FROM Vocabulary v JOIN v.languages l WHERE v.user = :user AND l IN :languages")
    List<Vocabulary> findByUserAndLanguagesIn(@Param("user") User user,
                                              @Param("languages") List<Language> languages);
}
