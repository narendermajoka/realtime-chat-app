package com.intuit.assignment.chatserver.service;

import com.intuit.assignment.chatserver.model.ChatRoomMessage;
import com.intuit.assignment.chatserver.model.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    List<ChatRoom> getAvailableChatRooms();

    ChatRoom createChatRoom(Long userId, String roomName);

    void joinUserInChatRoom(Long userId, Long roomId);

    List<ChatRoomMessage> getChatRoomMessages(Long roomId);
}
