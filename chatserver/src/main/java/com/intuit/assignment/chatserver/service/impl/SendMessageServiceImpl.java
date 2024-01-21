package com.intuit.assignment.chatserver.service.impl;

import com.intuit.assignment.chatserver.constants.MessageConstants;
import com.intuit.assignment.chatserver.model.ChatRoomMessage;
import com.intuit.assignment.chatserver.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMessageServiceImpl implements SendMessageService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendMessageToChatRoom(ChatRoomMessage message) {
        messagingTemplate.convertAndSend(MessageConstants.TOPIC+message.getChatRoomName() , message);
    }
}
