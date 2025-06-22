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

    @PostMapping("users/{userId}/vocab")
    public ResponseEntity<VocabDto> addWord(@PathVariable Long userId, @RequestBody VocabDto vocabularyDto) {
        return new ResponseEntity<>(vocabularyService.addWord(userId, vocabularyDto), HttpStatus.CREATED);
    }

    @GetMapping("users/{userId}/vocab/lang")
    public ResponseEntity<List<VocabDto>> getWordsByLanguage(@PathVariable Long id, @RequestParam List<String> languages){
        List<VocabDto> wordsByLanguage = vocabularyService.getWordsByLanguages(id, languages);
        return ResponseEntity.ok(wordsByLanguage);
    }

    @GetMapping("users/{userId}/vocab")
    public List<VocabDto> getWordsByUserId(@PathVariable Long userId) {
        return vocabularyService.getWordsByUserId(userId);
    }

    @GetMapping("users/{userId}/vocab/{id}")
    public ResponseEntity<VocabDto> getWordsById(@PathVariable Long userId, @PathVariable Long id) {
        VocabDto wordById = vocabularyService.getWordById(id, userId);
        return new ResponseEntity<>(wordById, HttpStatus.OK);
    }

    @PutMapping("users/{userId}/vocab/{id}")
    public ResponseEntity<VocabDto> updateWord(@PathVariable Long userId, @PathVariable Long id,
                                               @RequestBody VocabDto vocabularyDto) {
        VocabDto updatedWord = vocabularyService.updateWord(userId, id, vocabularyDto);
        return new ResponseEntity<>(updatedWord, HttpStatus.OK);
    }

    @DeleteMapping("users/{userId}/vocab/{id}")
    public ResponseEntity<String> deleteWord(@PathVariable Long userId, @PathVariable Long id) {
        vocabularyService.deleteWord(userId, id);
        return new ResponseEntity<>("Word deleted successfully", HttpStatus.OK);
    }

}
