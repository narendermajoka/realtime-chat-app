package com.company.assignment.chatserver.service;

import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.ChatRoomMessageResponse;

public interface ISendMessageService {
    ChatRoomMessageResponse sendMessageToChatRoom(ChatRoomMessage message);
}
