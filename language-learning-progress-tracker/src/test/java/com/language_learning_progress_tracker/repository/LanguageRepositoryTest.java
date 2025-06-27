package com.language_learning_progress_tracker.repository;

import com.language_learning_progress_tracker.entity.Language;
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
class LanguageRepositoryTest {

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    void LanguageRepository_Save_ReturnSavedLanguage() {

        //Arrange
        Language language = new Language
                ("English");

        //Act
        Language savedLanguage = languageRepository.save(language);

        //Assert
        Assertions.assertNotNull(savedLanguage);
        Assertions.assertTrue(savedLanguage.getId() >= 1L);
    }

    @Test
    void LanguageRepository_GetALLReturnMoreThanOne() {
        Language language1 = new Language("Spanish");
        Language language2 = new Language("Portuguese");

        languageRepository.save(language1);
        languageRepository.save(language2);

        List<Language> languageList = languageRepository.findAll();

        Assertions.assertNotNull(languageList);
        Assertions.assertTrue(languageList.size() > 1);

    }

    @Test
    void LanguageRepository_FindById_ReturnsLanguage() {
        Language language = new Language("Korean");

        languageRepository.save(language);

        Language languageById = languageRepository.findById(language.getId()).get();

        Assertions.assertNotNull(languageById);
    }

    @Test
    void LanguageRepository_FindByName_ReturnLanguageNotNull() {
        Language language = new Language("Korean");

        languageRepository.save(language);

        Language languageByName = languageRepository.findByName(language.getName()).get();

        Assertions.assertNotNull(languageByName);

    }

    @Test
    void LanguageRepository_UpdateLanguage_ReturnLanguageNotNull() {
        Language language = new Language("Korean");

        languageRepository.save(language);

        Language languageSaved = languageRepository.findById(language.getId()).get();
        languageSaved.setName("Japonese");

        Language updatedLanguage = languageRepository.save(languageSaved);

        Assertions.assertNotNull(updatedLanguage.getName());

    }

    @Test
    void LanguageRepository_DeleteById_ReturnLanguageIsEmpty() {
        Language language = new Language("German");

        languageRepository.save(language);

        languageRepository.deleteById(language.getId());
        Optional<Language> byId = languageRepository.findById(language.getId());

        Assertions.assertTrue(byId.isEmpty());

    }
}