package com.language_learning_progress_tracker.controller;

import com.language_learning_progress_tracker.dto.LanguageDto;
import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.service.LanguageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {
    private LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<List<LanguageDto>> getAllLanguages() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> getLanguageById(@PathVariable Long id) {
        return ResponseEntity.ok(languageService.getLanguageById(id));
    }

    @GetMapping("/names")
    public ResponseEntity<List<LanguageDto>> getLanguagesByNames(@RequestParam List<String> names) {
        List<LanguageDto> result = languageService.getLanguagesByNames(names);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{userId}/{langId}")
    public ResponseEntity<LanguageDto> updateLanguage(
            @PathVariable Long userId,
            @PathVariable Long langId,
            @RequestBody LanguageDto languageDto){
        LanguageDto updatedLanguage = languageService.updateLanguage(userId, langId, languageDto);
        return new ResponseEntity<>(updatedLanguage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LanguageDto> createLanguage(@Valid @RequestBody LanguageDto languageDto) {
        LanguageDto created = languageService.createLanguage(languageDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        languageService.deleteLanguage(id);
        return ResponseEntity.noContent().build();
    }
}

