package com.company.assignment.chatserver.controller;

import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.service.IChatRoomService;
import com.company.assignment.chatserver.service.ISendMessageService;
import com.company.assignment.chatserver.entity.ChatRoomMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@Controller
@CrossOrigin(origins = "*")
public class SendMessageController {
    @Autowired
    private ISendMessageService sendMessageService;

    @MessageMapping("/message/chat-room")
    private ChatRoomMessage sendMessageToChatRoom(@Payload ChatRoomMessage message){
        sendMessageService.sendMessageToChatRoom(message);
        return message;
    }
}
