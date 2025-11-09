package com.example.translator.service;

import com.example.translator.exception.TranslationException;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

import java.util.Map;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.translator.dao.FileDao;
import com.example.translator.dao.LanguageDao;
import com.example.translator.dao.TranslationDao;
import com.example.translator.dto.Translation;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationDao translationDao;
    private final Translate googleTranslate;

    @Transactional
    public Translation addTranslation(Long folderId, String sourceLang, String targetLang,
                                      String originalText, String translatedText, Long fileId) {
        log.info("Saving translation for folder: {}", folderId);
        return translationDao.addTranslation(folderId, sourceLang, targetLang,
                originalText, translatedText, fileId);
    }

    @Transactional(readOnly = true)
    public Translation getTranslation(Long translationId) {
        return translationDao.getTranslation(translationId);
    }

    public String translateWithGoogle(String text, String targetLanguage) {
        try {
            log.info("Google translation: {} -> {}",
                    text.substring(0, Math.min(20, text.length())), targetLanguage);

            com.google.cloud.translate.Translation translation = googleTranslate.translate(
                    text,
                    Translate.TranslateOption.targetLanguage(targetLanguage)
            );
            return translation.getTranslatedText();
        } catch (Exception ex) {
            log.error("Google translation failed", ex);
            throw new TranslationException("Google 번역에 실패했습니다", ex);
        }
    }

    public String translate(String provider, String sourceLang, String targetLang, String text) {
        if ("google".equalsIgnoreCase(provider)) {
            return translateWithGoogle(text, targetLang);
        } else {
            throw new IllegalArgumentException("지원하지 않는 번역 제공자: " + provider);
        }
    }
}