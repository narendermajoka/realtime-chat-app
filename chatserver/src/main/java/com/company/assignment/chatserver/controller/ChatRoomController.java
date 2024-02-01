package com.company.assignment.chatserver.controller;

import com.company.assignment.chatserver.constants.MessageConstants;
import com.company.assignment.chatserver.entity.ChatRoomMessageEntity;
import com.company.assignment.chatserver.model.*;
import com.company.assignment.chatserver.service.IChatRoomService;
import com.company.assignment.chatserver.util.ApplicationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat/room")
@CrossOrigin(origins = "*")
@Tag(name = "Chat Room", description = "Chat Room APIs")
public class ChatRoomController {
    @Autowired
    private IChatRoomService chatRoomService;

    @PostMapping
    @Operation(summary = "Create a new chat room")
    @PreAuthorize("hasAuthority('CREATE_ROOM')")
    public ResponseWrapper<Boolean> createRoom(@Valid @RequestBody ChatRoom chatRoom) {
        Long userId = ApplicationUtil.getCurrentUser().getUserId();
        chatRoomService.createChatRoom(userId, chatRoom);
        return new ResponseWrapper<>(MessageConstants.ROOM_CREATED);
    }

    @DeleteMapping("/{chatRoomId}")
    @Operation(summary = "Delete a chat room")
    @PreAuthorize("hasAuthority('DELETE_ROOM')")
    public ResponseWrapper<Boolean> deleteRoom(@PathVariable("chatRoomId") Long chatRoomId){
        chatRoomService.deleteChatRoom(chatRoomId);
        return new ResponseWrapper<>(MessageConstants.ROOM_DELETED);
    }

    @GetMapping("/{chatRoomId}/messages")
    @Operation(summary = "Get all messages of a chat room")
    @PreAuthorize("hasAuthority('READ_ROOM_MESSAGES')")
    public ResponseWrapper<List<ChatRoomMessage>> getChatRoomMessages(@PathVariable("chatRoomId")  Long roomId){
        Long userId = ApplicationUtil.getCurrentUser().getUserId();
        List<ChatRoomMessage> messages = chatRoomService.getChatRoomMessages(userId, roomId);
        return new ResponseWrapper<>(messages);
    }

    @PutMapping("/{chatRoomId}/join/user/{userId}")
    @Operation(summary = "Join a user in a chat room")
    @PreAuthorize("hasAuthority('ADD_USER_IN_ROOM')")
    public ResponseWrapper<Boolean> joinChatRoom(@PathVariable("chatRoomId") Long chatRoomId, @PathVariable("userId") Long userId) {
        chatRoomService.joinUserInChatRoom(userId, chatRoomId);
        return new ResponseWrapper<>(true, MessageConstants.USER_JOINED_ROOM);
    }


    @GetMapping
    @Operation(summary = "Get all available chat rooms")
    @PreAuthorize("hasAuthority('READ_ALL_ROOMS')")
    public ResponseWrapper<List<ChatRoomResponse>> getAvailableChatRooms() {
        Long userId = ApplicationUtil.getCurrentUser().getUserId();
        return new ResponseWrapper<>(chatRoomService.getAvailableChatRooms(userId));
    }

    @PostMapping("/message")
    @Operation(summary = "Send a message in a chat room", hidden = true)
    @PreAuthorize("hasAuthority('WRITE_MESSAGE_IN_ROOM')")
    public ResponseWrapper<String> sendMessageInChatRoom(@RequestBody ChatRoomMessage message){
        chatRoomService.saveChatRoomMessage(message);
        return new ResponseWrapper<>(MessageConstants.MESSAGE_SENT_TO_ROOM);
    }
}
