package com.intuit.assignment.chatserver.controller;

import com.intuit.assignment.chatserver.model.ChatRoomMessage;
import com.intuit.assignment.chatserver.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class SendMessageController {
    @Autowired
    private SendMessageService sendMessageService;

    @MessageMapping("/message/chat-room")
    private ChatRoomMessage sendMessageToChatRoom(@Payload ChatRoomMessage message){
        sendMessageService.sendMessageToChatRoom(message);
        return message;
    }
}
