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

    public VocabularyService(VocabRepository vocabRepository, UserRepository userRepository) {
        this.vocabRepository = vocabRepository;
        this.userRepository = userRepository;
    }

    public VocabDto addWord(Long id, VocabDto vocabularyDto) {
        Vocabulary vocabulary = mapToEntity(vocabularyDto);
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with associated language not found"));
        vocabulary.setUser(user);

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

    public List<VocabDto> getWordsByLanguage(Long userId, String language) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Language language1 = languageRepository.findByName(language).orElseThrow(() -> new LanguageNotFoundException("Language not found!"));

        List<Vocabulary> words = vocabRepository.findByUserAndLanguage(user, language1);

        return words.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public VocabDto updateWord(Long userId, Long vocabId, VocabDto vocabularyDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated word not found"));
        Vocabulary vocabulary = vocabRepository.findById(vocabId).orElseThrow(() -> new UserNotFoundException("User with associated word not found"));

        if (vocabulary.getUser().getId() != user.getId()) {
            throw new WordNotFoundException("This word does not belong to a user");
        }

        vocabulary.setWord(vocabularyDto.getWord());
        vocabulary.setMeaning(vocabularyDto.getMeaning());
        vocabulary.setLanguage(vocabularyDto.getLanguage());
        vocabulary.setExample(vocabularyDto.getExample());

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
        VocabDto vocabularyDto = new VocabDto();
        vocabularyDto.setId(vocabulary.getId());
        vocabularyDto.setWord(vocabulary.getWord());
        vocabularyDto.setExample(vocabulary.getExample());
        vocabularyDto.setMeaning(vocabulary.getMeaning());
        vocabularyDto.setLanguage(vocabulary.getLanguage());
        return vocabularyDto;
    }

    private Vocabulary mapToEntity(VocabDto vocabularyDto) {
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setId(vocabularyDto.getId());
        vocabulary.setUser(vocabularyDto.getUser());
        vocabulary.setLanguage(vocabularyDto.getLanguage());
        vocabulary.setMeaning(vocabularyDto.getMeaning());
        return vocabulary;

    }
}
