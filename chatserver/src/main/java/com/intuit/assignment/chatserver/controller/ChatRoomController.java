package com.intuit.assignment.chatserver.controller;

import com.intuit.assignment.chatserver.model.ChatRoomMessage;
import com.intuit.assignment.chatserver.model.ChatRoom;
import com.intuit.assignment.chatserver.model.ResponseWrapper;
import com.intuit.assignment.chatserver.service.ChatRoomService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("/api/v1/chat/room")
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/")
    public ResponseWrapper<Boolean> createRoom(@RequestBody String chatRoomName) {
        Long userId = null;
        ChatRoom chatRoom = chatRoomService.createChatRoom(userId, chatRoomName);
        chatRoomService.joinUserInChatRoom(chatRoom.getRoomId(), userId);
        return new ResponseWrapper<>(true, null);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseWrapper<List<ChatRoomMessage>> getChatRoomMessages(@PathVariable("chatRoomId")  Long roomId){
        List<ChatRoomMessage> messages = chatRoomService.getChatRoomMessages(roomId);
        return new ResponseWrapper<>(messages, null);
    }

    @PostMapping("/{chatRoomId}/join/user")
    public ResponseWrapper<List<ChatRoomMessage>> joinChatRoom(@PathVariable("roomId") Long roomId) {
        Long userId = null;
        chatRoomService.joinUserInChatRoom(roomId, userId);
        List<ChatRoomMessage> messages = chatRoomService.getChatRoomMessages(roomId);
        return new ResponseWrapper<>(messages, null);
    }


    @GetMapping("/")
    public ResponseWrapper<List<ChatRoom>> getAvailableChatRooms() {
        return new ResponseWrapper<>(chatRoomService.getAvailableChatRooms(), null);
    }
}
