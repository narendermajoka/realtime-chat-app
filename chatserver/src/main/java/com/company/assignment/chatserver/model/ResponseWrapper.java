package com.company.assignment.chatserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWrapper<T> {
    private T data;
    private String message;

    public ResponseWrapper(String message){
        this.message = message;
    }

    public ResponseWrapper(T data){
        this.data = data;
    }

}
