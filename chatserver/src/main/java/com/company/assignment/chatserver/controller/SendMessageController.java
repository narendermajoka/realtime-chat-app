package com.company.assignment.chatserver.controller;

import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "*")
public class SendMessageController {
    @Autowired
    private ISendMessageService sendMessageService;

    @MessageMapping("/message/chat-room/{chatRoomId}")
    @SendTo("/topic/chatrooms.{chatRoomId}")
    public ChatRoomMessage sendMessageToChatRoom(@Payload ChatRoomMessage message, @DestinationVariable String chatRoomId){
        return sendMessageService.sendMessageToChatRoom(message);
    }
}
