package com.company.assignment.chatserver.service.impl;

import com.company.assignment.chatserver.constants.MessageConstants;
import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.ChatRoomMessageResponse;
import com.company.assignment.chatserver.service.IChatRoomService;
import com.company.assignment.chatserver.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService implements ISendMessageService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private IChatRoomService chatRoomService;

    @Override
    public ChatRoomMessageResponse sendMessageToChatRoom(ChatRoomMessage message) {
        ChatRoomMessageResponse roomMessageResponse = chatRoomService.saveChatRoomMessage(message);
        messagingTemplate.convertAndSend(MessageConstants.TOPIC+message.getChatRoomId(), roomMessageResponse);
        return roomMessageResponse;
    }
}
