package com.language_learning_progress_tracker.service;

import com.language_learning_progress_tracker.dto.VocabDto;
import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.entity.Vocabulary;
import com.language_learning_progress_tracker.exception.LanguageNotFoundException;
import com.language_learning_progress_tracker.exception.UserNotFoundException;
import com.language_learning_progress_tracker.exception.WordNotFoundException;
import com.language_learning_progress_tracker.repository.LanguageRepository;
import com.language_learning_progress_tracker.repository.UserRepository;
import com.language_learning_progress_tracker.repository.VocabRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VocabularyService {
    private VocabRepository vocabRepository;
    private UserRepository userRepository;
    private LanguageRepository languageRepository;

    public VocabularyService(VocabRepository vocabRepository, UserRepository userRepository, LanguageRepository languageRepository) {
        this.vocabRepository = vocabRepository;
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
    }

    public VocabDto addWord(Long id, VocabDto vocabularyDto) {
        Vocabulary vocabulary = mapToEntity(vocabularyDto);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with associated language not found"));
        vocabulary.setUser(user);

        List<Language> languages = languageRepository.findByNameIn(vocabularyDto.getLanguages());

        if (languages.size() != vocabularyDto.getLanguages().size()) {
            throw new LanguageNotFoundException("Some languages not found");
        }

        vocabulary.setLanguages(languages);

        Vocabulary newWord = vocabRepository.save(vocabulary);

        return mapToDto(newWord);
    }


    public VocabDto getWordById(Long wordId, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not associated with any id"));
        Vocabulary byId = vocabRepository.findById(wordId).orElseThrow(() -> new WordNotFoundException("Word not associated with any id"));

        if (!Objects.equals(byId.getUser().getId(), user.getId())) {
            throw new WordNotFoundException("This word is not associated to any user");
        }
        return mapToDto(byId);
    }

    public List<VocabDto> getWordsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        List<Vocabulary> words = vocabRepository.findByUser(user);

        return words.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<VocabDto> getWordsByLanguages(Long userId, List<String> languageNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        List<Language> languages = languageRepository.findByNameIn(languageNames);

        if (languages.isEmpty()) {
            throw new LanguageNotFoundException("No valid languages found");
        }

        List<Vocabulary> words = vocabRepository.findByUserAndLanguagesIn(user, languages);

        return words.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public VocabDto updateWord(Long userId, Long vocabId, VocabDto vocabularyDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with associated word not found"));

        Vocabulary vocabulary = vocabRepository.findById(vocabId)
                .orElseThrow(() -> new UserNotFoundException("User with associated word not found"));

        if (!vocabulary.getUser().getId().equals(user.getId())) {
            throw new WordNotFoundException("This word does not belong to the user");
        }

        vocabulary.setWord(vocabularyDto.getWord());
        vocabulary.setMeaning(vocabularyDto.getMeaning());
        vocabulary.setExample(vocabularyDto.getExample());

        List<Language> languages = languageRepository.findByNameIn(vocabularyDto.getLanguages());
        if (languages.size() != vocabularyDto.getLanguages().size()) {
            throw new LanguageNotFoundException("Some languages not found");
        }
        vocabulary.setLanguages(languages);

        Vocabulary updatedVocab = vocabRepository.save(vocabulary);

        return mapToDto(updatedVocab);
    }


    public void deleteWord(Long userId, Long vocabId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated word not found"));
        Vocabulary vocabulary = vocabRepository.findById(vocabId).orElseThrow(() -> new UserNotFoundException("User with associated word not found"));

        if (vocabulary.getUser().getId() != user.getId()) {
            throw new WordNotFoundException("This word does not belong to a user");
        }

        vocabRepository.delete(vocabulary);
    }

    private VocabDto mapToDto(Vocabulary vocabulary) {
        VocabDto dto = new VocabDto();
        dto.setId(vocabulary.getId());
        dto.setWord(vocabulary.getWord());
        dto.setExample(vocabulary.getExample());
        dto.setMeaning(vocabulary.getMeaning());

        List<String> languageNames = vocabulary.getLanguages().stream()
                .map(Language::getName)
                .collect(Collectors.toList());
        dto.setLanguages(languageNames);

        return dto;
    }


    private Vocabulary mapToEntity(VocabDto dto) {
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setId(dto.getId());
        vocabulary.setWord(dto.getWord());
        vocabulary.setExample(dto.getExample());
        vocabulary.setMeaning(dto.getMeaning());
        return vocabulary;
    }

}
