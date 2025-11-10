package com.example.translator.controller;

import com.example.translator.dto.ProviderTranslateRequest;
import com.example.translator.dto.TranslationRequest;
import com.example.translator.dto.TranslationResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.translator.dto.Translation;
import com.example.translator.service.TranslationService;

import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequestMapping("/api/translation")
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    // 번역 저장 (수정됨)
    @PostMapping("/save")
    public ResponseEntity<?> translateAndSave(
            @RequestParam Long folderId,
            @RequestParam String sourceLang,
            @RequestParam String targetLang,
            @RequestParam String originalText,
            @RequestParam(required = false) Long fileId) {

        try {
            log.info("Translation save request: folder={}, {}→{}", folderId, sourceLang, targetLang);

            // Google 번역
            String translatedText = translationService.translateWithGoogle(originalText, targetLang);

            // DB 저장
            Translation saved = translationService.addTranslation(
                    folderId, sourceLang, targetLang, originalText, translatedText, fileId
            );

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            log.error("Failed to save translation", e);
            return ResponseEntity.internalServerError()
                    .body("{\"message\": \"번역 저장에 실패했습니다: " + e.getMessage() + "\"}");
        }
    }

    // 단일 번역 조회
    @GetMapping("/{id}")
    public ResponseEntity<Translation> getTranslation(@PathVariable("id") Long translationId) {
        Translation translation = translationService.getTranslation(translationId);
        if (translation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(translation);
    }

    // 즉시 번역 (저장 안함)
    @PostMapping("/instant")
    public ResponseEntity<String> instantTranslate(
            @RequestParam String text,
            @RequestParam String targetLang) {

        try {
            log.info("Instant translation: {} chars → {}", text.length(), targetLang);
            String translatedText = translationService.translateWithGoogle(text, targetLang);
            return ResponseEntity.ok(translatedText);
        } catch (Exception e) {
            log.error("Translation failed", e);
            return ResponseEntity.internalServerError()
                    .body("번역에 실패했습니다: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getTranslations() {
        // 임시 테스트용
        return ResponseEntity.ok("Translation API connected successfully");
    }
}