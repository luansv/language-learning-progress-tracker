package com.language_learning_progress_tracker.controller;

import com.language_learning_progress_tracker.dto.VocabDto;
import com.language_learning_progress_tracker.service.VocabularyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class VocabularyController {
    private VocabularyService vocabularyService;

    public VocabularyController(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @PostMapping("user/{userId}/vocab")
    public ResponseEntity<VocabDto> addWord(@PathVariable Long userId, @RequestBody VocabDto vocabularyDto) {
        return new ResponseEntity<>(vocabularyService.addWord(userId, vocabularyDto), HttpStatus.CREATED);
    }

    @GetMapping("user/{userId}/vocab/lang")
    public ResponseEntity<List<VocabDto>> getWordsByLanguage(@PathVariable Long userId, @RequestParam List<String> languages){
        List<VocabDto> wordsByLanguage = vocabularyService.getWordsByLanguages(userId, languages);
        return ResponseEntity.ok(wordsByLanguage);
    }

    @GetMapping("user/{userId}/vocab")
    public List<VocabDto> getWordsByUserId(@PathVariable Long userId) {
        return vocabularyService.getWordsByUserId(userId);
    }

    @GetMapping("user/{userId}/vocab/{id}")
    public ResponseEntity<VocabDto> getWordsById(@PathVariable Long userId, @PathVariable Long id) {
        VocabDto wordById = vocabularyService.getWordById(id, userId);
        return new ResponseEntity<>(wordById, HttpStatus.OK);
    }

    @PutMapping("user/{userId}/vocab/{id}")
    public ResponseEntity<VocabDto> updateWord(@PathVariable Long userId, @PathVariable Long id,
                                               @RequestBody VocabDto vocabularyDto) {
        VocabDto updatedWord = vocabularyService.updateWord(userId, id, vocabularyDto);
        return new ResponseEntity<>(updatedWord, HttpStatus.OK);
    }

    @DeleteMapping("user/{userId}/vocab/{id}")
    public ResponseEntity<String> deleteWord(@PathVariable Long userId, @PathVariable Long id) {
        vocabularyService.deleteWord(userId, id);
        return new ResponseEntity<>("Word deleted successfully", HttpStatus.OK);
    }

}
