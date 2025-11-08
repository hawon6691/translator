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

public class User {
    public Long user_id;
    public String username;
    public String email;
    public String password;
    public LocalDateTime created_at;
}
