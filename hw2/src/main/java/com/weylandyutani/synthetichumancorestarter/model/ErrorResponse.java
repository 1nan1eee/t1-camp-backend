package com.weylandyutani.synthetichumancorestarter.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String errorCode;
    private final String message;
    private final LocalDateTime timestamp;
}
