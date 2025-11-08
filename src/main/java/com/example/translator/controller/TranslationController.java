package com.example.translator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.translator.dto.Translation;
import com.example.translator.service.TranslationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/translation")
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    @PostMapping("/save")
    public ResponseEntity<Translation> translateAndSave(
            @RequestParam Long folderId,
            @RequestParam String sourceLang,
            @RequestParam String targetLang,
            @RequestParam String originalText,
            @RequestParam(required = false) Long fileId) {

        // Google 번역 (Papago로 바꾸려면 translateWithPapago 사용)
        String translatedText = translationService.translateWithGoogle(originalText, targetLang);

        // DB 저장
        Translation saved = translationService.addTranslation(folderId, sourceLang, targetLang, originalText,
                translatedText, fileId);

        return ResponseEntity.ok(saved);
    }

    // 단일 번역 기록 조회
    @GetMapping("/{id}")
    public ResponseEntity<Translation> getTranslation(@PathVariable("id") Long translationId) {
        Translation translation = translationService.getTranslation(translationId);
        if (translation == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(translation);
    }

    // 임시: DB 저장 없이 바로 번역만
    @PostMapping("/instant")
    public ResponseEntity<String> instantTranslate(
            @RequestParam String text,
            @RequestParam String targetLang) {

        String translatedText = translationService.translateWithGoogle(text, targetLang);
        return ResponseEntity.ok(translatedText);
    }

    @PostMapping("/provider")
    public ResponseEntity<String> translate(
            @RequestParam String provider,
            @RequestParam String sourceLang,
            @RequestParam String targetLang,
            @RequestParam String text) {

        String translated = translationService.translate(provider, sourceLang, targetLang, text);
        return ResponseEntity.ok(translated);
    }
}
