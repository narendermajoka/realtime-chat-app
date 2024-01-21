package com.intuit.assignment.chatserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatRoom {
    private Long roomId;
    private String roomName;
    private Long owner;
    private boolean isLoggedInUserJoined;

    public ChatRoom(Long roomId, String roomName, Long owner) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.owner = owner;
    }
}
