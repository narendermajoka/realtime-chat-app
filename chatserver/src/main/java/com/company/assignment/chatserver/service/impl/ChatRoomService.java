package com.company.assignment.chatserver.service.impl;

import com.company.assignment.chatserver.constants.MessageConstants;
import com.company.assignment.chatserver.entity.ChatRoomEntity;
import com.company.assignment.chatserver.entity.ChatRoomMessageEntity;
import com.company.assignment.chatserver.auth.entity.UserEntity;
import com.company.assignment.chatserver.exception.ChatRoomException;
import com.company.assignment.chatserver.model.ChatRoom;
import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.ChatRoomResponse;
import com.company.assignment.chatserver.repository.ChatRoomMessageRepository;
import com.company.assignment.chatserver.repository.ChatRoomRepository;
import com.company.assignment.chatserver.auth.repository.UserEntityRepository;
import com.company.assignment.chatserver.repository.mapper.ChatRoomMapper;
import com.company.assignment.chatserver.service.IChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatRoomService implements IChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private ChatRoomMessageRepository chatRoomMessageRepository;

    @Override
    public List<ChatRoomResponse> getAvailableChatRooms(Long userId) {
        log.info("Fetching all chat rooms created in system");

        List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.findAllByOrderByCreatedAtAsc();
        return chatRoomEntities.stream()
                .map((chatRoomEntity) -> {
                    ChatRoomResponse chatRoomResponse = ChatRoomMapper.fromChatRoomEntity(chatRoomEntity);
                    boolean isLoggedInUserMember =  Boolean.TRUE.equals(chatRoomRepository.existsByRoomIdAndUserId(chatRoomEntity.getRoomId(), userId));
                    chatRoomResponse.setLoggedInUserMember(isLoggedInUserMember);
                    return chatRoomResponse;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ChatRoomEntity createChatRoom(Long userId, ChatRoom roomRequest) {
        log.info("creating chat room with name: {}", roomRequest.getChatRoomName());

        if (isChatRoomAlreadyExists(roomRequest.getChatRoomName())) {
            throw new ChatRoomException("Chat Room: " + roomRequest.getChatRoomName() + " already exists.", null);
        }
        UserEntity userEntity = userEntityRepository.findById(userId).get();

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .roomName(roomRequest.getChatRoomName())
                .description(roomRequest.getChatRoomDescription())
                .owner(userEntity)
                .build();
        chatRoomEntity.addMember(userEntity);

        chatRoomRepository.save(chatRoomEntity);
        return chatRoomEntity;
    }

    @Transactional
    @Override
    public void deleteChatRoom(Long chatRoomId) {
        log.info("deleting chat room with room_id: {}", chatRoomId);
        chatRoomRepository.deleteById(chatRoomId);
    }

    @Override
    public void joinUserInChatRoom(Long userId, Long roomId) {
        log.info("Adding user: {} in chat room: {}", userId, roomId);
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
    public List<ChatRoomMessage> getChatRoomMessages(Long userId, Long roomId) {
        log.info("User: {}, fetching chat room messages for roomId: {}", userId, roomId);

        Optional<ChatRoomEntity> chatRoom = chatRoomRepository.findChatRoomWithUser(roomId, userId);
        return chatRoom.map((chatRoomEntity) -> {
                    return chatRoomEntity.getChatRoomMessages()
                            .stream()
                            .map(ChatRoomMapper::fromChatRoomMessageEntity)
                            .collect(Collectors.toList());
                })
                .orElseThrow(() -> new ChatRoomException(MessageConstants.SELECT_VALID_CHAT_ROOM));
    }

    @Override
    public ChatRoomMessage saveChatRoomMessage(ChatRoomMessage messageRequest) {
        log.info("storing message from user:{} in roomId: {}",messageRequest.getSenderId(), messageRequest.getChatRoomId());

        Optional<ChatRoomEntity> chatRoom = chatRoomRepository.findById(messageRequest.getChatRoomId());
        if (chatRoom.isEmpty()) {
            throw new ChatRoomException(MessageConstants.CHAT_ROOM_NOT_EXISTS);
        }
        Optional<UserEntity> userEntity = userEntityRepository.findById(messageRequest.getSenderId());
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("Sender doesn't exists.");
        }
        if (messageRequest.getSentAt() == null) {
            messageRequest.setSentAt(LocalDateTime.now());
        }
        ChatRoomMessageEntity messageEntity = ChatRoomMessageEntity.builder()
                .chatRoom(chatRoom.get())
                .sender(userEntity.get())
                .textMessage(messageRequest.getTextMessage())
                .sentAt(messageRequest.getSentAt())
                .build();
        chatRoomMessageRepository.save(messageEntity);
        return ChatRoomMapper.fromChatRoomMessageEntity(messageEntity);

    }

    private boolean isChatRoomAlreadyExists(String chatRoomName) {
        return chatRoomRepository.existsByRoomName(chatRoomName);
    }
}
