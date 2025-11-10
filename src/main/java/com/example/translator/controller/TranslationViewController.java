package com.example.translator.controller;

import com.example.translator.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/translations")
@RequiredArgsConstructor
class TranslationViewController {
    private final TranslationService translationService;

    @GetMapping
    public String listTranslations(
            @RequestParam(required = false) Long folderId,
            Model model) {

        try {
            // 폴더별 번역 목록 (현재는 구현 안됨)
            // List<Translation> translations = translationService.getTranslationsByFolder(folderId);
            // model.addAttribute("translations", translations);

            model.addAttribute("folderId", folderId);
            return "translation-list";
        } catch (Exception e) {
            log.error("Failed to load translations", e);
            model.addAttribute("errorMessage", "번역 목록을 불러오는데 실패했습니다.");
            return "error";
        }
    }
}