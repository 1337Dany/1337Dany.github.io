package org.example.s30050tpo06.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class TranslationService {
    private final Map<String, Map<String, String>> translations = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("languages/translations.txt");

        if (inputStream == null) {
            throw new FileNotFoundException("Translation file not found in resources");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                int dotIndex = line.indexOf('.');
                if (dotIndex <= 0 || dotIndex == line.length() - 1) {
                    System.err.println("Invalid format at line " + lineNumber + ": " + line);
                    continue;
                }

                String lang = line.substring(0, dotIndex).trim();
                String restOfLine = line.substring(dotIndex + 1).trim();

                int equalIndex = restOfLine.indexOf('=');
                if (equalIndex <= 0 || equalIndex == restOfLine.length() - 1) {
                    System.err.println("Invalid format at line " + lineNumber + ": " + line);
                    continue;
                }

                String key = restOfLine.substring(0, equalIndex).trim();
                String value = restOfLine.substring(equalIndex + 1).trim();

                translations
                        .computeIfAbsent(lang, k -> new HashMap<>())
                        .put(key, value);
            }
        }
    }

    public String getTranslation(String language, String key) {
        Objects.requireNonNull(key, "Translation key cannot be null");

        Map<String, String> langTranslations = translations.get(language);

        if (langTranslations == null || !langTranslations.containsKey(key)) {
            langTranslations = translations.get("en");
        }
        return langTranslations != null ? langTranslations.getOrDefault(key, key) : key;
    }
}