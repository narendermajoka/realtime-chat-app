package com.company.assignment.chatserver.service.impl;

import com.company.assignment.chatserver.constants.MessageConstants;
import com.company.assignment.chatserver.entity.ChatRoomEntity;
import com.company.assignment.chatserver.entity.ChatRoomMessageEntity;
import com.company.assignment.chatserver.entity.UserEntity;
import com.company.assignment.chatserver.exception.ChatRoomException;
import com.company.assignment.chatserver.model.ChatRoom;
import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.ChatRoomMessageResponse;
import com.company.assignment.chatserver.model.ChatRoomResponse;
import com.company.assignment.chatserver.repository.ChatRoomMessageRepository;
import com.company.assignment.chatserver.repository.ChatRoomRepository;
import com.company.assignment.chatserver.repository.UserEntityRepository;
import com.company.assignment.chatserver.repository.mapper.ChatRoomMapper;
import com.company.assignment.chatserver.service.IChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatRoomService implements IChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private ChatRoomMessageRepository chatRoomMessageRepository;

    @Override
    public List<ChatRoomResponse> getAvailableChatRooms() {
        List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.findAll();
        return chatRoomEntities.stream()
                .map(ChatRoomMapper::fromChatRoomEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ChatRoomEntity createChatRoom(Long userId, ChatRoom roomRequest) {
        if (isChatRoomAlreadyExists(roomRequest.getChatRoomName())) {
            throw new ChatRoomException("Chat Room: " + roomRequest.getChatRoomName() + " already exists.", null);
        }
        UserEntity userEntity = userEntityRepository.findById(userId).get();

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .roomName(roomRequest.getChatRoomName())
                .owner(userEntity)
                .build();
        chatRoomEntity.addMember(userEntity);

        chatRoomRepository.save(chatRoomEntity);
        return chatRoomEntity;
    }

    @Override
    public void joinUserInChatRoom(Long userId, Long roomId) {
        Optional<ChatRoomEntity> roomEntityOptional = chatRoomRepository.findById(roomId);
        if (roomEntityOptional.isPresent()) {
            Optional<UserEntity> userEntityOptional = userEntityRepository.findById(userId);
            if (userEntityOptional.isPresent()) {
                ChatRoomEntity chatRoom = roomEntityOptional.get();
                chatRoom.addMember(userEntityOptional.get());
                chatRoomRepository.save(chatRoom);
            } else {
                throw new UsernameNotFoundException("User doesn't exists.");
            }
        } else {
            throw new ChatRoomException(MessageConstants.CHAT_ROOM_NOT_EXISTS);
        }
    }

    @Override
    public List<ChatRoomMessageResponse> getChatRoomMessages(Long roomId) {
        Optional<ChatRoomEntity> chatRoom = chatRoomRepository.findById(roomId);
        return chatRoom.map((chatRoomEntity) -> {
                    return chatRoomEntity.getChatRoomMessages()
                            .stream()
                            .map(ChatRoomMapper::fromChatRoomMessageEntity)
                            .collect(Collectors.toList());
                })
                .orElseThrow(() -> new ChatRoomException(MessageConstants.CHAT_ROOM_NOT_EXISTS));
    }

    @Override
    public void saveChatRoomMessage(ChatRoomMessage messageRequest) {
        Optional<ChatRoomEntity> chatRoom = chatRoomRepository.findById(messageRequest.getChatRoomId());
        if (chatRoom.isEmpty()) {
            throw new ChatRoomException(MessageConstants.CHAT_ROOM_NOT_EXISTS);
        }
        Optional<UserEntity> userEntity = userEntityRepository.findById(messageRequest.getSenderId());
        if(userEntity.isEmpty()){
            throw new UsernameNotFoundException("Sender doesn't exists.");
        }
        if(messageRequest.getSentAt()==null){
            messageRequest.setSentAt(LocalDateTime.now());
        }
        ChatRoomMessageEntity messageEntity = ChatRoomMessageEntity.builder()
                .chatRoom(chatRoom.get())
                .sender(userEntity.get())
                .textMessage(messageRequest.getTextMessage())
                .sentAt(messageRequest.getSentAt())
                .build();
        chatRoomMessageRepository.save(messageEntity);

    }

    private boolean isChatRoomAlreadyExists(String chatRoomName) {
        return chatRoomRepository.existsByRoomName(chatRoomName);
    }
}
