package com.example.translator.config;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TranslationConfig {

    @Bean
    public Translate googleTranslate() {
        try {
            // 환경변수 GOOGLE_APPLICATION_CREDENTIALS를 통해 인증
            return TranslateOptions.getDefaultInstance().getService();
        } catch (Exception e) {
            // Google API 키가 없을 경우 더미 객체 반환 (개발 환경)
            System.err.println("Warning: Google Translate API not configured properly");
            System.err.println("Set GOOGLE_APPLICATION_CREDENTIALS environment variable");
            return null; // 실제로는 Mock 객체를 반환하는 것이 좋음
        }
    }
}