package com.language_learning_progress_tracker.service;

import com.language_learning_progress_tracker.dto.LanguageDto;
import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.exception.LanguageNotFoundException;
import com.language_learning_progress_tracker.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
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

    public LanguageDto getLanguageByName(String name) {
        Language language = languageRepository.findByName(name)
                .orElseThrow(() -> new LanguageNotFoundException("Language not found: " + name));
        return mapToDto(language);
    }

    public LanguageDto createLanguage(LanguageDto languageDto) {
        Language language = mapToEntity(languageDto);
        Language saved = languageRepository.save(language);
        return mapToDto(saved);
    }

    public void deleteLanguage(Long id) {
        if (!languageRepository.existsById(id)) {
            throw new LanguageNotFoundException("Language not found with ID: " + id);
        }
        languageRepository.deleteById(id);
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

