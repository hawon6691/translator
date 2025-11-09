package com.example.translator.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "password")
public class User {
    private Long user_id;
    private String username;
    private String email;

    @JsonIgnore // JSON 응답에서 비밀번호 제외
    private String password;

    private LocalDateTime created_at;
}