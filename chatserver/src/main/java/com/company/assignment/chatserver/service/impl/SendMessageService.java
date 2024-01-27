package com.company.assignment.chatserver.service.impl;

import com.company.assignment.chatserver.constants.MessageConstants;
import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.MessageType;
import com.company.assignment.chatserver.model.UserInfo;
import com.company.assignment.chatserver.service.IChatRoomService;
import com.company.assignment.chatserver.service.ISendMessageService;
import com.company.assignment.chatserver.util.ApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService implements ISendMessageService {
    @Autowired
    private IChatRoomService chatRoomService;

    @Override
    public ChatRoomMessage sendMessageToChatRoom(ChatRoomMessage message) {
        ChatRoomMessage roomMessageResponse = null;
        if(message.getMessageType() == MessageType.MESSAGE){
            roomMessageResponse = chatRoomService.saveChatRoomMessage(message);
        }else{
            roomMessageResponse = new ChatRoomMessage();
            roomMessageResponse.setSenderFullName(message.getSenderFullName());
            roomMessageResponse.setMessageType(message.getMessageType());
            roomMessageResponse.setChatRoomId(message.getChatRoomId());
        }
        return roomMessageResponse;
    }
}
