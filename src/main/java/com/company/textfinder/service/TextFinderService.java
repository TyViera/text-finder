package com.company.textfinder.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TextFinderService {

    public List<String> findText(String searchText, List<String> collectionText) {
        return findText(searchText, collectionText, Boolean.TRUE);
    }

    public List<String> findText(String searchText, List<String> collectionText, Boolean caseSensitive) {
        validateInputs(searchText);
        validateInputs(collectionText);
        Map<String, String> specialGivenCharacters = new HashMap<>();

        searchText = searchText.trim().replaceAll(" +", " ");
        fillSpecialCharactersValues(searchText, specialGivenCharacters);
        searchText = replaceSpecialCharacters(searchText, specialGivenCharacters);
        if (!caseSensitive) {
            searchText = searchText.toLowerCase();
        }
        String[] wordsToSearch = searchText.split("\\s");
        return collectionText
                .parallelStream()
                .filter(this::isNotEmptyString)
                .filter(word -> validateSearch(wordsToSearch, word, specialGivenCharacters, caseSensitive))
                .collect(Collectors.toList());
    }

    private void validateInputs(String searchText) {
        if (searchText == null) {
            throw new RuntimeException("The given string argument should not be null");
        }
        if (searchText.isEmpty()) {
            throw new RuntimeException("The given string argument should not be an empty list");
        }
    }

    private void validateInputs(List<String> collectionText) {
        if (collectionText == null) {
            throw new RuntimeException("The given collection argument should not be null");
        }
        if (collectionText.isEmpty()) {
            throw new RuntimeException("The given collection argument should not be an empty list");
        }
    }

    private boolean isNotEmptyString(String value) {
        return value != null && !value.isEmpty();
    }

    private boolean validateSearch(String[] wordsToSearch, String phrase,
            Map<String, String> specialGivenCharacters, Boolean caseSensitive) {
        return Arrays
                .stream(wordsToSearch)
                .filter(word -> workWithOneWord(word, phrase, specialGivenCharacters, caseSensitive))
                .count() == wordsToSearch.length;
    }

    private boolean workWithOneWord(String searchText, String phrase,
            Map<String, String> specialGivenCharacters, Boolean caseSensitive) {
        phrase = replaceSpecialCharacters(phrase, specialGivenCharacters);
        if (!caseSensitive) {
            phrase = phrase.toLowerCase();
        }
        String regex = ".*\\b" + searchText + "\\b.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phrase);
        return matcher.matches();
    }

    private String replaceSpecialCharacters(String source, Map<String, String> specialGivenCharacters) {
        AtomicReference<String> resultValue = new AtomicReference<>(source);
        specialGivenCharacters
                .keySet()
                .stream()
                .forEach(special -> resultValue.set(resultValue.get().replace(special, specialGivenCharacters.get(special))));
        return resultValue.get();
    }

    private void fillSpecialCharactersValues(String searchText, Map<String, String> specialGivenCharacters) {
        String[] specials = new String[]{"\"", "-", "+", "*", "/", "?", ".", ",", ";", "'",
            "(", ")", "[", "]", "{", "}"};
        Arrays.stream(specials)
                .filter(special -> searchText.contains(special))
                .forEach(special -> specialGivenCharacters.put(special, UUID.randomUUID().toString()));
    }

}
