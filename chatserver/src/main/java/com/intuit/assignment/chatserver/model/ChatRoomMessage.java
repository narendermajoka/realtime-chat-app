package com.intuit.assignment.chatserver.model;

import lombok.Data;

import java.util.Date;
@Data
public class ChatRoomMessage {
    private Long senderId;
    private String chatRoomName;
    private String message;
    private Date messaged_at;
    private MessageStatus status;
}
