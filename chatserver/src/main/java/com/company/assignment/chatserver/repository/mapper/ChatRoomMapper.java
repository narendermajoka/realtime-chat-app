package com.company.assignment.chatserver.repository.mapper;

import com.company.assignment.chatserver.entity.ChatRoomEntity;
import com.company.assignment.chatserver.entity.ChatRoomMessageEntity;
import com.company.assignment.chatserver.model.ChatRoomMessageResponse;
import com.company.assignment.chatserver.model.ChatRoomResponse;

public class ChatRoomMapper {
    private ChatRoomMapper() {
    }

    public static ChatRoomResponse fromChatRoomEntity(ChatRoomEntity chatRoomEntity) {
        ChatRoomResponse chatRoomResponse = new ChatRoomResponse();
        chatRoomResponse.setChatRoomId(chatRoomEntity.getRoomId());
        chatRoomResponse.setChatRoomName(chatRoomEntity.getRoomName());
        chatRoomResponse.setOwnerId(chatRoomEntity.getOwner().getUserId());
        chatRoomResponse.setOwnerName(chatRoomEntity.getOwner().getFullName());
        return chatRoomResponse;
    }

    public static ChatRoomMessageResponse fromChatRoomMessageEntity(ChatRoomMessageEntity messageEntity) {
        ChatRoomMessageResponse roomMessageResponse = new ChatRoomMessageResponse();
        roomMessageResponse.setChatRoomId(messageEntity.getChatRoom().getRoomId());
        roomMessageResponse.setChatRoomName(messageEntity.getChatRoom().getRoomName());
        roomMessageResponse.setSenderId(messageEntity.getSender().getUserId());
        roomMessageResponse.setSenderFullName(messageEntity.getSender().getFullName());
        roomMessageResponse.setTextMessage(messageEntity.getTextMessage());
        return roomMessageResponse;
    }

}
