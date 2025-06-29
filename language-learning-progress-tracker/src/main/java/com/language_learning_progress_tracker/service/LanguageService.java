package com.language_learning_progress_tracker.service;

import com.language_learning_progress_tracker.dto.LanguageDto;
import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.exception.LanguageNotFoundException;
import com.language_learning_progress_tracker.exception.UserNotFoundException;
import com.language_learning_progress_tracker.repository.LanguageRepository;
import com.language_learning_progress_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final UserRepository userRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository, UserRepository userRepository) {
        this.languageRepository = languageRepository;
        this.userRepository = userRepository;
    }

    public List<LanguageDto> getAllLanguages() {
        return languageRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public LanguageDto getLanguageById(Long id) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new LanguageNotFoundException("Language not found with ID: " + id));
        return mapToDto(language);
    }

    public List<LanguageDto> getLanguagesByNames(List<String> names) {
        List<Language> languages = languageRepository.findByNameIn(names);

        if (languages.size() != names.size()) {
            throw new LanguageNotFoundException("Some languages were not found");
        }

        return languages.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public LanguageDto createLanguage(LanguageDto languageDto) {
        Language language = mapToEntity(languageDto);
        Language saved = languageRepository.save(language);
        return mapToDto(saved);
    }

    public LanguageDto updateLanguage(Long userId, Long langId, LanguageDto languageDto){
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found"));
        Language language = languageRepository.findById(langId)
                .orElseThrow(() -> new LanguageNotFoundException("Language not found"));

        language.setName(languageDto.getName());

        Language updatedLang = languageRepository.save(language);
        return mapToDto(updatedLang);
    }

    public void deleteLanguage(Long id) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new LanguageNotFoundException("Language not found"));
        languageRepository.delete(language);
    }

    private LanguageDto mapToDto(Language language) {
        LanguageDto dto = new LanguageDto();
        dto.setId(language.getId());
        dto.setName(language.getName());
        return dto;
    }

    private Language mapToEntity(LanguageDto dto) {
        Language language = new Language();
        language.setId(dto.getId());
        language.setName(dto.getName());
        return language;
    }
}

