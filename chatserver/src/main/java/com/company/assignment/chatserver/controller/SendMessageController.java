package com.company.assignment.chatserver.controller;

import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "*")
public class SendMessageController {
    @Autowired
    private ISendMessageService sendMessageService;

    @MessageMapping("/message/chat-room")
    public ChatRoomMessage sendMessageToChatRoom(@Payload ChatRoomMessage message){
        return sendMessageService.sendMessageToChatRoom(message);
    }
}
