package com.company.assignment.chatserver.controller;

import com.company.assignment.chatserver.constants.MessageConstants;
import com.company.assignment.chatserver.entity.ChatRoomMessageEntity;
import com.company.assignment.chatserver.model.*;
import com.company.assignment.chatserver.service.IChatRoomService;
import com.company.assignment.chatserver.util.ApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat/room")
@CrossOrigin(origins = "*")
public class ChatRoomController {
    @Autowired
    private IChatRoomService chatRoomService;

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseWrapper<Boolean> createRoom(@RequestBody ChatRoom chatRoom) {
        Long userId = ApplicationUtil.getCurrentUser().getUserId();
        chatRoomService.createChatRoom(userId, chatRoom);
        return new ResponseWrapper<>(MessageConstants.ROOM_CREATED);
    }

    @DeleteMapping("/{chatRoomId}")
    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
    public ResponseWrapper<Boolean> deleteRoom(@PathVariable("chatRoomId") Long chatRoomId){
        chatRoomService.deleteChatRoom(chatRoomId);
        return new ResponseWrapper<>(MessageConstants.ROOM_DELETED);
    }

    @GetMapping("/{chatRoomId}/messages")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseWrapper<List<ChatRoomMessage>> getChatRoomMessages(@PathVariable("chatRoomId")  Long roomId){
        Long userId = ApplicationUtil.getCurrentUser().getUserId();
        List<ChatRoomMessage> messages = chatRoomService.getChatRoomMessages(userId, roomId);
        return new ResponseWrapper<>(messages);
    }

    @PutMapping("/{chatRoomId}/join/user/{userId}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseWrapper<Boolean> joinChatRoom(@PathVariable("chatRoomId") Long chatRoomId, @PathVariable("userId") Long userId) {
        chatRoomService.joinUserInChatRoom(userId, chatRoomId);
        return new ResponseWrapper<>(true, MessageConstants.USER_JOINED_ROOM);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseWrapper<List<ChatRoomResponse>> getAvailableChatRooms() {
        Long userId = ApplicationUtil.getCurrentUser().getUserId();
        return new ResponseWrapper<>(chatRoomService.getAvailableChatRooms(userId));
    }

    @PostMapping("/message")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseWrapper<String> sendMessageInChatRoom(@RequestBody ChatRoomMessage message){
        chatRoomService.saveChatRoomMessage(message);
        return new ResponseWrapper<>(MessageConstants.MESSAGE_SENT_TO_ROOM);
    }
}
