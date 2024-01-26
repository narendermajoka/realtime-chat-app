package com.company.assignment.chatserver.exception;

public class ChatRoomException extends RuntimeException{
    public ChatRoomException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
    public ChatRoomException(String errorMessage){
        super(errorMessage);
    }
}
