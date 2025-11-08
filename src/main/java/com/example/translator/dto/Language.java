package com.example.translator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Language {
    public String lang_code;
    public String lang_name;

    public Language(String lang_code, String lang_name) {
        this.lang_code = lang_code;
        this.lang_name = lang_name;
    }
}
