package com.springSecuirty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ExDetails> customerExceptionHandler(CustomerException ce, WebRequest wr){

        ExDetails exDetails = new ExDetails();
        exDetails.setMessage(ce.getMessage());
        exDetails.setTimeStamp(LocalDateTime.now());
        exDetails.setDetails(wr.getDescription(false));

        return new ResponseEntity<>(exDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExDetails> GlobalExceptionHandler(Exception ce, WebRequest wr){

        ExDetails exDetails = new ExDetails();
        exDetails.setMessage(ce.getMessage());
        exDetails.setTimeStamp(LocalDateTime.now());
        exDetails.setDetails(wr.getDescription(false));

        return new ResponseEntity<>(exDetails, HttpStatus.BAD_REQUEST);
    }
}
