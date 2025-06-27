package com.language_learning_progress_tracker.repository;

import com.language_learning_progress_tracker.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class VocabRepositoryTest {

    @Autowired
    private VocabRepository vocabRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    void VocabRepository_Save_ReturnSavedVocab() {

        //Arrange
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);
        Language language = new Language();
        language.setName("German");
        languageRepository.save(language);
        List<Language> languages = List.of(language);

        Vocabulary vocabulary = new Vocabulary("Apple",
                "I think the apple is rotten right to core", "Fruit", user, languages);

        //Act
        Vocabulary savedVocab = vocabRepository.save(vocabulary);

        //Assert
        Assertions.assertNotNull(savedVocab);
        Assertions.assertTrue(savedVocab.getId() >= 1L);
    }

    @Test
    void VocabRepository_GetAllReturnMoreThanOne() {
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);
        Language language = new Language();
        language.setName("German");
        languageRepository.save(language);
        List<Language> languages = List.of(language);

        Vocabulary vocabulary = new Vocabulary("Apple", "I think the apple is rotten right to core", "Fruit", user, languages);
        Vocabulary vocabulary2 = new Vocabulary("Summer", "Renaissance", "", user, languages);

        vocabRepository.save(vocabulary);
        vocabRepository.save(vocabulary2);

        List<Vocabulary> all = vocabRepository.findAll();

        Assertions.assertNotNull(all);
        Assertions.assertTrue(all.size() > 1);

    }

    @Test
    void VocabRepository_FindById_ReturnsVocab() {
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);
        Language language = new Language();
        language.setName("German");
        languageRepository.save(language);
        List<Language> languages = List.of(language);

        Vocabulary vocabulary = new Vocabulary("Apple",
                "I think the apple is rotten right to core", "Fruit", user, languages);

        Vocabulary saved = vocabRepository.save(vocabulary);

        vocabRepository.findById(saved.getId()).get();

        Assertions.assertNotNull(saved);
    }

    @Test
    void VocabRepository_FindByUserId_ReturnVocab() {
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);
        Language language = new Language();
        language.setName("German");
        languageRepository.save(language);
        List<Language> languages = List.of(language);

        Vocabulary vocabulary = new Vocabulary("Apple",
                "I think the apple is rotten right to core", "Fruit", user, languages);

        Vocabulary saved = vocabRepository.save(vocabulary);

        List<Vocabulary> byUserId = vocabRepository.findByUserId(saved.getUser().getId());

        Assertions.assertNotNull(byUserId);
    }

    @Test
    void VocabRepository_FindByUserAndLanguage_ReturnUserNotNull() {
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);
        Language language = new Language();
        language.setName("German");
        languageRepository.save(language);
        List<Language> languages = List.of(language);

        Vocabulary vocabulary = new Vocabulary("Apple",
                "I think the apple is rotten right to core", "Fruit", user, languages);

        vocabRepository.save(vocabulary);

        List<Vocabulary> byUserAndLanguagesIn = vocabRepository.findByUserAndLanguagesIn(vocabulary.getUser(), vocabulary.getLanguages());

        Assertions.assertNotNull(byUserAndLanguagesIn);

    }

    @Test
    void VocabRepository_UpdateVocab_ReturnVocabNotNull() {
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);

        Language language = new Language();
        language.setName("German");
        languageRepository.save(language);

        List<Language> languages = new ArrayList<>();
        languages.add(language);

        Vocabulary vocabulary = new Vocabulary("Apple",
                "I think the apple is rotten right to core", "Fruit", user, languages);

        vocabRepository.save(vocabulary);

        Vocabulary savedVocabulary = vocabRepository.findById(vocabulary.getId()).get();
        savedVocabulary.setExample("nsd na snd as");

        Vocabulary saved = vocabRepository.save(savedVocabulary);

        Assertions.assertNotNull(saved.getExample());

    }

    @Test
    void VocabRepository_DeleteById_ReturnVocabIsEmpty() {
        User user = new User("user", "password", UserRole.USER);
        userRepository.save(user);
        Language language = new Language();
        language.setName("German");
        languageRepository.save(language);
        List<Language> languages = List.of(language);

        Vocabulary vocabulary = new Vocabulary("Apple",
                "I think the apple is rotten right to core", "Fruit", user, languages);

        vocabRepository.save(vocabulary);

        vocabRepository.deleteById(vocabulary.getId());
        Optional<Vocabulary> byId = vocabRepository.findById(vocabulary.getId());

        Assertions.assertTrue(byId.isEmpty());

    }
}
