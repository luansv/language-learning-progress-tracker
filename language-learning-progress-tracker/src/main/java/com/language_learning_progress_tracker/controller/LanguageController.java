package com.language_learning_progress_tracker.controller;

import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.service.LanguageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {
    private LanguageService languageService;

    @GetMapping
    public List<Language> getAll() {
        return languageService.getAllLanguages();
    }

    @PostMapping
    public Language addLanguage(@RequestBody Language language) {
        return languageService.addLanguage(language);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        languageService.deleteLanguage(id);
    }
}

