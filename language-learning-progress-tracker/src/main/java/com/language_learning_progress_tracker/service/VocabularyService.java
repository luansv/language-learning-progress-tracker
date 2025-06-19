package com.language_learning_progress_tracker.service;

import com.language_learning_progress_tracker.dto.VocabDto;
import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.entity.Vocabulary;
import com.language_learning_progress_tracker.exception.UserNotFoundException;
import com.language_learning_progress_tracker.repository.UserRepository;
import com.language_learning_progress_tracker.repository.VocabRepository;
import org.springframework.stereotype.Service;

@Service
public class VocabularyService {
    private VocabRepository vocabRepository;
    private UserRepository userRepository;

    public VocabularyService(VocabRepository vocabRepository) {
        this.vocabRepository = vocabRepository;
    }

    public VocabDto addWord(Long id, VocabDto vocabularyDto) {
        Vocabulary vocabulary = mapToEntity(vocabularyDto);
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with associated language not found"));
        vocabulary.setUser(user);

        Vocabulary newWord = vocabRepository.save(vocabulary);

        return mapToDto(newWord);
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
