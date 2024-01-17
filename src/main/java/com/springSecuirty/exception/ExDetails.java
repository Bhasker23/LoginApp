package com.springSecuirty.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExDetails {

    private LocalDateTime timeStamp;
    private String message;
    private String details;
}
