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

public class Folder {
    public Long folder_id;
    public Long user_id;
    public String folder_name;
    public LocalDateTime created_at;
}
