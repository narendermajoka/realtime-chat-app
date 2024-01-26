package com.company.assignment.chatserver.service;

import com.company.assignment.chatserver.entity.ChatRoomEntity;
import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.ChatRoom;
import com.company.assignment.chatserver.model.ChatRoomMessageResponse;
import com.company.assignment.chatserver.model.ChatRoomResponse;

import java.util.List;

public interface IChatRoomService {
    List<ChatRoomResponse> getAvailableChatRooms();

    ChatRoomEntity createChatRoom(Long userId, ChatRoom roomName);

    void joinUserInChatRoom(Long userId, Long roomId);

    List<ChatRoomMessageResponse> getChatRoomMessages(Long roomId);

    ChatRoomMessageResponse saveChatRoomMessage(ChatRoomMessage chatRoomMessage);
}
