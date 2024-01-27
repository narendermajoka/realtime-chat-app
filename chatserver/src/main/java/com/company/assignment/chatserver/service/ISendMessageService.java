package com.company.assignment.chatserver.service;

import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.ChatRoomMessage;

public interface ISendMessageService {
    ChatRoomMessage sendMessageToChatRoom(ChatRoomMessage message);
}
