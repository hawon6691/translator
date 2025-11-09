package com.example.translator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslationRequest {
    @NotNull(message = "폴더 ID는 필수입니다")
    private Long folderId;

    @NotBlank(message = "원본 언어는 필수입니다")
    private String sourceLang;

    @NotBlank(message = "대상 언어는 필수입니다")
    private String targetLang;

    @NotBlank(message = "원본 텍스트는 필수입니다")
    private String originalText;

    private Long fileId;
}