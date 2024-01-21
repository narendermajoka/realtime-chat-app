package com.intuit.assignment.chatserver.exception;

public class ChatRoomAlreadyExistsException extends RuntimeException{
    public ChatRoomAlreadyExistsException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
