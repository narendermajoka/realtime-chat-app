package com.company.assignment.chatserver.controller.advice;

import com.company.assignment.chatserver.exception.ChatRoomException;
import com.company.assignment.chatserver.model.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.StringJoiner;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error, please contact customer support.";

    @ExceptionHandler({ChatRoomException.class})
    public ResponseEntity<ResponseWrapper<String>> handleChatRoomException(ChatRoomException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper<>(null, exception.getMessage()));
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ResponseWrapper<String>> accessDeniedException(AccessDeniedException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseWrapper<>(null, exception.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ResponseWrapper<String>> illegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper<>(null, ex.getMessage()));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ResponseWrapper<String>> badCredentialsException(BadCredentialsException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper<>(null, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        StringJoiner responseMessage = new StringJoiner(",");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            responseMessage.add(fieldName+ " "+errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper<>(null, responseMessage.toString()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseWrapper<String>> handleGenericException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseWrapper<>(null, INTERNAL_SERVER_ERROR));
    }
}
