package com.company.assignment.chatserver.repository.mapper;

import com.company.assignment.chatserver.entity.ChatRoomEntity;
import com.company.assignment.chatserver.entity.ChatRoomMessageEntity;
import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.ChatRoomResponse;
import com.company.assignment.chatserver.model.MessageType;

public class ChatRoomMapper {
    private ChatRoomMapper() {
    }

    public static ChatRoomResponse fromChatRoomEntity(ChatRoomEntity chatRoomEntity) {
        ChatRoomResponse chatRoomResponse = new ChatRoomResponse();
        chatRoomResponse.setChatRoomId(chatRoomEntity.getRoomId());
        chatRoomResponse.setChatRoomName(chatRoomEntity.getRoomName());
        chatRoomResponse.setChatRoomDescription(chatRoomEntity.getDescription());
        chatRoomResponse.setOwnerId(chatRoomEntity.getOwner().getUserId());
        chatRoomResponse.setOwnerName(chatRoomEntity.getOwner().getFullName());
        return chatRoomResponse;
    }

    public static ChatRoomMessage fromChatRoomMessageEntity(ChatRoomMessageEntity messageEntity) {
        ChatRoomMessage chatRoomMessage = new ChatRoomMessage();
        chatRoomMessage.setChatRoomId(messageEntity.getChatRoom().getRoomId());
        chatRoomMessage.setChatRoomName(messageEntity.getChatRoom().getRoomName());
        chatRoomMessage.setSenderId(messageEntity.getSender().getUserId());
        chatRoomMessage.setSenderFullName(messageEntity.getSender().getFullName());
        chatRoomMessage.setTextMessage(messageEntity.getTextMessage());
        chatRoomMessage.setMessageType(MessageType.MESSAGE);
        chatRoomMessage.setSentAt(messageEntity.getSentAt());
        return chatRoomMessage;
    }

}
