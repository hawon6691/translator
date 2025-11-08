package com.example.translator.controller;

import com.example.translator.service.GoogleTranslationService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/google-translate")
@RequiredArgsConstructor
public class GoogleTranslationController {

    private final GoogleTranslationService translationService;

    // JSON Body를 받도록 수정
    @PostMapping
    public ResponseEntity<String> translate(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String targetLang = request.get("targetLang");

        if (text == null || targetLang == null) {
            return ResponseEntity.badRequest().body("text와 targetLang 필수");
        }

        String translated = translationService.translateText(text, targetLang);
        return ResponseEntity.ok(translated);
    }

    @GetMapping
    public ResponseEntity<String> translateGet(@RequestParam String text,
                                               @RequestParam String targetLang) {
        String translated = translationService.translateText(text, targetLang);
        return ResponseEntity.ok(translated);
    }
}