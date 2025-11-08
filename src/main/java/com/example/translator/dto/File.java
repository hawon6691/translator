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

public class File {
    public Long file_id;
    public String file_name;
    public String file_path;
    public String file_type;
    public LocalDateTime uploaded_at;
}
