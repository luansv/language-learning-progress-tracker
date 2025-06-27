package com.language_learning_progress_tracker.repository;

import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.Lesson;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.entity.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class LessonRepositoryTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    void LessonRepository_Save_ReturnSavedLesson() {

        //Arrange
        User user = new User("user", "password", UserRole.USER);
        List<Language> languages = new ArrayList(List.of("DSAD"));
        Lesson lesson = new Lesson("Grammar", "AN vs A", languages, user);

        //Act
        lessonRepository.save(lesson);

        //Assert
        Assertions.assertNotNull(lesson);
        Assertions.assertTrue(lesson.getId() >= 1L);
    }

    @Test
    void LessonRepository_GetAllReturnMoreThanOne() {
        User user = new User("user", "password", UserRole.USER);
        User user2 = new User("julia", "password", UserRole.USER);
        userRepository.save(user);
        userRepository.save(user2);

        Language language = new Language();
        language.setName("DSAD");
        languageRepository.save(language);

        List<Language> languages = List.of(language);

        Lesson lesson1 = new Lesson("Grammar", "AN vs A", languages, user);
        Lesson lesson2 = new Lesson("Grammar", "DO vs DOES", languages, user2);

        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);

        List<Lesson> lessonList = lessonRepository.findAll();

        Assertions.assertNotNull(lessonList);
        Assertions.assertTrue(lessonList.size() > 1);
    }


    @Test
    void LessonRepository_FindById_ReturnsLesson() {
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);

        Language language = new Language();
        language.setName("DSAD");
        languageRepository.save(language);
        List<Language> languages = List.of(language);

        Lesson lesson1 = new Lesson("Grammar", "AN vs A", languages, user);

        lessonRepository.save(lesson1);

        Lesson lesson = lessonRepository.findById(lesson1.getId()).get();

        Assertions.assertNotNull(lesson);
    }

    @Test
    void LessonRepository_findByUserAndLanguages_ReturnLessonNotNull() {
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);

        Language language = new Language();
        language.setName("German");
        languageRepository.save(language);
        List<Language> languages = List.of(language);

        Lesson lesson1 = new Lesson("Grammar", "AN vs A", languages, user);

        lessonRepository.save(lesson1);

        lessonRepository.findByUserAndLanguagesIn(user, languages);

        Assertions.assertNotNull(lesson1);

    }

    @Test
    void LessonRepository_UpdateLesson_ReturnLessonNotNull() {
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);

        Language language = new Language();
        language.setName("German");
        languageRepository.save(language);

        List<Language> languages = new ArrayList<>();
        languages.add(language);

        Lesson lesson1 = new Lesson("Grammar", "AN vs A", languages, user);

        lessonRepository.save(lesson1);

        Lesson lessonSaved = lessonRepository.findById(lesson1.getId()).get();
        lessonSaved.setTitle("Anki");

        Lesson updatedLesson = lessonRepository.save(lessonSaved);

        Assertions.assertNotNull(updatedLesson.getTitle());

    }

    @Test
    void LessonRepository_DeleteById_ReturnLessonIsEmpty() {
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);

        Language language = new Language();
        language.setName("German");
        languageRepository.save(language);
        List<Language> languages = List.of(language);

        Lesson lesson1 = new Lesson("Grammar", "AN vs A", languages, user);
        lessonRepository.save(lesson1);

        lessonRepository.deleteById(lesson1.getId());
        Optional<Lesson> byId = lessonRepository.findById(lesson1.getId());

        Assertions.assertTrue(byId.isEmpty());

 }
}