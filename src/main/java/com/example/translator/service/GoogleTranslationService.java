package com.example.translator.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.stereotype.Service;

@Service
public class GoogleTranslationService {

    private final Translate translate;

    public GoogleTranslationService() {
        // 환경 변수 GOOGLE_APPLICATION_CREDENTIALS를 통해 인증
        this.translate = TranslateOptions.getDefaultInstance().getService();
    }

    public String translateText(String text, String targetLanguage) {
        Translation translation = translate.translate(
                text,
                Translate.TranslateOption.targetLanguage(targetLanguage)
        );
        return translation.getTranslatedText();
    }
}