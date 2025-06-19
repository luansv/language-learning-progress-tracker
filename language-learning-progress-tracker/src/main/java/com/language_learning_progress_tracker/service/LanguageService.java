package com.language_learning_progress_tracker.service;

import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    private LanguageRepository languageRepository;

    public List<Language> getAllLanguages(){
        return languageRepository.findAll();
    }

    public Language addLanguage(Language language){
        return languageRepository.save(language);
    }

    public void deleteLanguage(Long id){
        languageRepository.deleteById(id);
    }
}
