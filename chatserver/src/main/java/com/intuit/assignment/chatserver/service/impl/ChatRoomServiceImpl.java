package com.intuit.assignment.chatserver.service.impl;

import com.intuit.assignment.chatserver.exception.ChatRoomAlreadyExistsException;
import com.intuit.assignment.chatserver.model.ChatRoom;
import com.intuit.assignment.chatserver.model.ChatRoomMessage;
import com.intuit.assignment.chatserver.service.ChatRoomService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private static List<ChatRoom> list = new ArrayList<>();
    static {
        list.add(new ChatRoom(1L,"Java", 0L));
        list.add(new ChatRoom(2L,"Spring", 0L));
        list.add(new ChatRoom(3L,"Angular", 0L));
    }
    @Override
    public List<ChatRoom> getAvailableChatRooms() {
        return list;
    }

    @Override
    public ChatRoom createChatRoom(Long userId, String chatRoomName) {
        if(isChatRoomAlreadyExists(chatRoomName)){
            throw new ChatRoomAlreadyExistsException("Chat Room: "+ chatRoomName +" already exists.", null);
        }
        //create chat room in database
        ChatRoom chatRoom = new ChatRoom(0L, chatRoomName, userId);
        list.add(chatRoom);
        return chatRoom;
    }

    @Override
    public void joinUserInChatRoom(Long userId, Long roomId) {

    }

    @Override
    public List<ChatRoomMessage> getChatRoomMessages(Long roomId) {
        return null;
    }

    private boolean isChatRoomAlreadyExists(String chatRoomName) {
        return false;
    }
}
