package com.example.translator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderTranslateRequest {
    @NotBlank(message = "번역 제공자는 필수입니다")
    private String provider;

    @NotBlank(message = "원본 언어는 필수입니다")
    private String sourceLang;

    @NotBlank(message = "대상 언어는 필수입니다")
    private String targetLang;

    @NotBlank(message = "텍스트는 필수입니다")
    private String text;
}