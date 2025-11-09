package com.example.translator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstantTranslateRequest {
    @NotBlank(message = "텍스트는 필수입니다")
    private String text;

    @NotBlank(message = "대상 언어는 필수입니다")
    private String targetLang;
}