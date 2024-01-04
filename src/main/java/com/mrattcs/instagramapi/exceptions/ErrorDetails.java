package com.mrattcs.instagramapi.exceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ErrorDetails {

    private String message;
    private String details;
    private LocalDateTime timestamp;

    public ErrorDetails(String message, String description, LocalDate now) {
    }

    public ErrorDetails(String message, String details, LocalDateTime timestamp) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }
}
