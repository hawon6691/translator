package com.example.translator.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Translation {
    public Long translation_id;
    public Long folder_id;
    public String source_lang;
    public String target_lang;
    public String original_text;
    public String translated_text;
    public Long file_id;
    public LocalDateTime created_at;
}
