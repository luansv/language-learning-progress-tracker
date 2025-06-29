package com.language_learning_progress_tracker.service;

import com.language_learning_progress_tracker.dto.LanguageDto;
import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.repository.LanguageRepository;
import com.language_learning_progress_tracker.repository.UserRepository;
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

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LanguageService languageService;

    @Test
    void LanguageService_getLanguagesById_ReturnLanguagesDTO() {
        Language language = new Language("German");
        language.setId(1L);

        when(languageRepository.findById(1L)).thenReturn(Optional.of(language));

        LanguageDto languageById1 = languageService.getLanguageById(1L);

        Assertions.assertNotNull(languageById1);
        Assertions.assertEquals("German", languageById1.getName());
    }


    @Test
    void LanguageService_getLanguagesByNames_ReturnsMatchingLanguagesDto() {
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
    void LanguageService_createLanguage_ReturnLanguageDto() {
        Language language = new Language("Spanish");
        LanguageDto languageDto = new LanguageDto();
        languageDto.setName("Spanish");

        when(languageRepository.save
                (Mockito.any(Language.class))).thenReturn(language);

        LanguageDto savedLanguage = languageService.createLanguage(languageDto);

        Assertions.assertNotNull(savedLanguage);
    }

    @Test
    void LanguageService_UpdateLanguage_ReturnUpdatedLang() {
        Long userId = 1L;
        Long languageId = 100L;

        User mockUser = new User();
        mockUser.setId(userId);

        Language existingLanguage = new Language();
        existingLanguage.setId(languageId);
        existingLanguage.setName("Old Name");

        LanguageDto inputDto = new LanguageDto();
        inputDto.setName("New Name");

        Language updatedLanguage = new Language();
        updatedLanguage.setId(languageId);
        updatedLanguage.setName("New Name");

        // Mock behaviors
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(languageRepository.findById(languageId)).thenReturn(Optional.of(existingLanguage));
        when(languageRepository.save(Mockito.any(Language.class))).thenReturn(updatedLanguage);
        // Act
        LanguageDto result = languageService.updateLanguage(userId, languageId, inputDto);

        // Assert
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals(languageId, result.getId());
    }

    @Test
    void LanguageService_DeleteLanguage_ReturnLanguageDto() {
        Language language = new Language();
        language.setId(1L);
        language.setName("Romanian");

        when(languageRepository.findById(1L)).thenReturn(Optional.of(language));

        assertDoesNotThrow(() -> languageService.deleteLanguage(1L));

    }
}