package com.intuit.assignment.chatserver.service;

import com.intuit.assignment.chatserver.model.ChatRoomMessage;

public interface SendMessageService {
    void sendMessageToChatRoom(ChatRoomMessage message);
}
