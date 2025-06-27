package com.language_learning_progress_tracker.service;

import com.language_learning_progress_tracker.dto.LanguageDto;
import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.repository.LanguageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageServiceTest {

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private LanguageService languageService;

    @Test
    void LanguageService_getLanguagesById_ReturnLanguages() {
        Language language = new Language("German");
        language.setId(1L);

        when(languageRepository.findById(1L)).thenReturn(Optional.of(language));

        LanguageDto languageById1 = languageService.getLanguageById(1L);

        Assertions.assertNotNull(languageById1);
        Assertions.assertEquals("German", languageById1.getName());
    }


    @Test
    void getLanguagesByNames_ReturnsMatchingLanguages() {
        List<String> names = List.of("German", "Thai");

        Language german = new Language("German");
        german.setId(1L);
        Language thai = new Language("Thai");
        thai.setId(2L);

        List<Language> languageList = List.of(german, thai);

        when(languageRepository.findByNameIn(names)).thenReturn(languageList);

        List<LanguageDto> result = languageService.getLanguagesByNames(names);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Thai", result.get(1).getName());
        Assertions.assertEquals("German", result.get(0).getName());
    }

    @Test
    void createLanguage() {
    }

    @Test
    void deleteLanguage() {
    }
}