package com.example.translator.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.translator.dao.LanguageDao;
import com.example.translator.dto.Language;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageDao languageDao;

    @Transactional
    public Language addLanguage(String langCode, String langName) {
        Language existing = getLanguage(langCode);
        if (existing != null) {
            throw new RuntimeException("이미 존재하는 언어 코드입니다: " + langCode);
        }
        return languageDao.addLanguage(langCode, langName);
    }

    @Transactional(readOnly = true)
    public Language getLanguage(String langCode) {
        return languageDao.getLanguage(langCode);
    }

    @Transactional(readOnly = true)
    public List<Language> getAllLanguages() {
        return languageDao.getAllLanguages();
    }
}
