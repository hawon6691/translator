package com.example.translator.controller;

import com.example.translator.dto.Language;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/languages")
public class LanguageController {

    private final List<Language> languageList = Arrays.asList(
            new Language("en", "English"),
            new Language("ko", "Korean"),
            new Language("ja", "Japanese"),
            new Language("fr", "French"),
            new Language("zh","Chinese")
    );

    @GetMapping
    public String listLanguages(Model model) {
        model.addAttribute("languages", languageList);
        return "language-list";
    }
}