package com.libs.msvc.commons.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String backedMessage;

    private String message;

    private int httpCode;

    @JsonFormat(pattern = "yyyy/MM/DD HH:mm:ss")
    private LocalDateTime time;
}

