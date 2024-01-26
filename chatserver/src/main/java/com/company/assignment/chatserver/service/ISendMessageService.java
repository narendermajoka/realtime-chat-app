package com.company.assignment.chatserver.service;

import com.company.assignment.chatserver.model.ChatRoomMessage;

public interface ISendMessageService {
    void sendMessageToChatRoom(ChatRoomMessage message);
}
