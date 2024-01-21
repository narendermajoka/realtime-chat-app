package com.intuit.assignment.chatserver.controller.advice;

import com.intuit.assignment.chatserver.exception.ChatRoomAlreadyExistsException;
import com.intuit.assignment.chatserver.model.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ChatRoomAlreadyExistsException.class})
    public ResponseEntity<ResponseWrapper<String>> handleRoomAlreadyExistsException(ChatRoomAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper<>(null, exception.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseWrapper<String>> handleGenericException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseWrapper<>(null, exception.getMessage()));
    }
}
