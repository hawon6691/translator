package com.example.translator.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.translator.dao.FileDao;
import com.example.translator.dao.LanguageDao;
import com.example.translator.dao.TranslationDao;
import com.example.translator.dto.Translation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final FileDao fileDao;
    private final LanguageDao languageDao;
    private final TranslationDao translationDao;
    // Google Translate용
    private final Translate googleTranslate = TranslateOptions.getDefaultInstance().getService();


    @Transactional
    public Translation addTranslation(Long folderId, String sourceLang, String targetLang,
            String originalText, String translatedText, Long fileId) {
        return translationDao.addTranslation(folderId, sourceLang, targetLang, originalText, translatedText, fileId);
    }

    @Transactional(readOnly = true)
    public Translation getTranslation(Long translationId) {
        return translationDao.getTranslation(translationId);
    }

    // Google 번역
    public String translateWithGoogle(String text, String targetLanguage) {
        com.google.cloud.translate.Translation translation =
                googleTranslate.translate(text, Translate.TranslateOption.targetLanguage(targetLanguage));
        return translation.getTranslatedText();
    }

    // Papago 번역
    public String translateWithPapago(String sourceLang, String targetLang, String text) {
        String url = "https://openapi.naver.com/v1/papago/n2mt";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "YOUR_CLIENT_ID");
        headers.add("X-Naver-Client-Secret", "YOUR_CLIENT_SECRET");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("source", sourceLang);
        params.add("target", targetLang);
        params.add("text", text);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        Map message = (Map) response.getBody().get("message");
        Map result = (Map) message.get("result");
        return (String) result.get("translatedText");
    }

    // 선택 번역: provider = "google" 또는 "papago"
    public String translate(String provider, String sourceLang, String targetLang, String text) {
        if ("google".equalsIgnoreCase(provider)) {
            return translateWithGoogle(text, targetLang);
        } else if ("papago".equalsIgnoreCase(provider)) {
            return translateWithPapago(sourceLang, targetLang, text);
        } else {
            throw new IllegalArgumentException("지원하지 않는 번역 제공자: " + provider);
        }
    }
}
