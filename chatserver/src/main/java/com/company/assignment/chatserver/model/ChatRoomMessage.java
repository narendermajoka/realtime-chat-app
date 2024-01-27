package com.company.assignment.chatserver.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatRoomMessage {
    private Long senderId;
    private Long chatRoomId;
    private String textMessage;
    private LocalDateTime sentAt;
    private MessageType messageType;
    private String senderFullName;
    private String chatRoomName;
}
