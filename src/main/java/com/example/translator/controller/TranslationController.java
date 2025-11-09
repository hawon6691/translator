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

    @PostMapping("/save")
    public ResponseEntity<Translation> translateAndSave(@Valid @RequestBody TranslationRequest request) {
        log.info("Translation request: {} -> {}", request.getSourceLang(), request.getTargetLang());

        String translatedText = translationService.translateWithGoogle(
                request.getOriginalText(),
                request.getTargetLang()
        );

        Translation saved = translationService.addTranslation(
                request.getFolderId(),
                request.getSourceLang(),
                request.getTargetLang(),
                request.getOriginalText(),
                translatedText,
                request.getFileId()
        );

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Translation> getTranslation(@PathVariable("id") Long translationId) {
        Translation translation = translationService.getTranslation(translationId);
        return ResponseEntity.ok(translation);
    }

    @PostMapping("/instant")
    public ResponseEntity<String> instantTranslate(
            @RequestParam String text,
            @RequestParam String targetLang) {

        log.info("Instant translation: {} chars -> {}", text.length(), targetLang);
        String translatedText = translationService.translateWithGoogle(text, targetLang);
        return ResponseEntity.ok(translatedText);
    }

    @PostMapping("/provider")
    public ResponseEntity<TranslationResponse> translateWithProvider(
            @Valid @RequestBody ProviderTranslateRequest request) {

        String translated = translationService.translate(
                request.getProvider(),
                request.getSourceLang(),
                request.getTargetLang(),
                request.getText()
        );

        return ResponseEntity.ok(new TranslationResponse(translated));
    }
}