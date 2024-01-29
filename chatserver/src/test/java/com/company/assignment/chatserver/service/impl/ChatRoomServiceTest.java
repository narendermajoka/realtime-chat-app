package com.company.assignment.chatserver.service.impl;

import com.company.assignment.chatserver.auth.entity.UserEntity;
import com.company.assignment.chatserver.auth.repository.UserEntityRepository;
import com.company.assignment.chatserver.entity.ChatRoomEntity;
import com.company.assignment.chatserver.entity.ChatRoomMessageEntity;
import com.company.assignment.chatserver.exception.ChatRoomException;
import com.company.assignment.chatserver.model.ChatRoom;
import com.company.assignment.chatserver.model.ChatRoomMessage;
import com.company.assignment.chatserver.model.MessageType;
import com.company.assignment.chatserver.repository.ChatRoomMessageRepository;
import com.company.assignment.chatserver.repository.ChatRoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ChatRoomService.class})
@ExtendWith(SpringExtension.class)
class ChatRoomServiceTest {

    @MockBean
    private ChatRoomMessageRepository chatRoomMessageRepository;

    @MockBean
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomService chatRoomService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    /**
     * Testing method:{@link ChatRoomService#getAvailableChatRooms(Long)}
     */
    @Test
    void testGetAvailableChatRooms() {
        when(chatRoomRepository.findAllByOrderByCreatedAtAsc()).thenReturn(new ArrayList<>());
        assertTrue(chatRoomService.getAvailableChatRooms(1L).isEmpty());
        verify(chatRoomRepository).findAllByOrderByCreatedAtAsc();
    }

    /**
     * Testing method:{@link ChatRoomService#getAvailableChatRooms(Long)}
     */
    @Test
    void testGetAvailableChatRooms2() {
        ArrayList<ChatRoomEntity> chatRoomEntityList = new ArrayList<>();
        ChatRoomEntity roomEntity = new ChatRoomEntity();
        roomEntity.setOwner(new UserEntity());
        chatRoomEntityList.add(roomEntity);
        when(chatRoomRepository.findAllByOrderByCreatedAtAsc()).thenReturn(chatRoomEntityList);
        assertEquals(1, chatRoomService.getAvailableChatRooms(1L).size());
        verify(chatRoomRepository).findAllByOrderByCreatedAtAsc();
    }

    /**
     * Testing method:{@link ChatRoomService#getAvailableChatRooms(Long)}
     */
    @Test
    void testGetAvailableChatRooms3() {
        when(chatRoomRepository.findAllByOrderByCreatedAtAsc()).thenThrow(new ChatRoomException("An error occurred"));
        assertThrows(ChatRoomException.class, () -> chatRoomService.getAvailableChatRooms(1L));
        verify(chatRoomRepository).findAllByOrderByCreatedAtAsc();
    }

    /**
     * Testing method:{@link ChatRoomService#getAvailableChatRooms(Long)}
     */
    @Test
    void testGetAvailableChatRooms4() {
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setOwner(new UserEntity());

        ArrayList<ChatRoomEntity> chatRoomEntityList = new ArrayList<>();
        chatRoomEntityList.add(chatRoomEntity);
        when(chatRoomRepository.existsByRoomIdAndUserId(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(true);
        when(chatRoomRepository.findAllByOrderByCreatedAtAsc()).thenReturn(chatRoomEntityList);
        assertEquals(1, chatRoomService.getAvailableChatRooms(1L).size());
        verify(chatRoomRepository).existsByRoomIdAndUserId(Mockito.<Long>any(), Mockito.<Long>any());
        verify(chatRoomRepository).findAllByOrderByCreatedAtAsc();
    }

    /**
     * Testing method:{@link ChatRoomService#getAvailableChatRooms(Long)}
     */
    @Test
    void testGetAvailableChatRooms5() {
        ChatRoomEntity chatRoomEntity = mock(ChatRoomEntity.class);
        when(chatRoomEntity.getOwner()).thenReturn(new UserEntity());
        when(chatRoomEntity.getRoomId()).thenReturn(1L);
        when(chatRoomEntity.getDescription()).thenReturn("Great room description");
        when(chatRoomEntity.getRoomName()).thenReturn("Room Name");
        doNothing().when(chatRoomEntity).setOwner(Mockito.<UserEntity>any());
        chatRoomEntity.setOwner(new UserEntity());

        ArrayList<ChatRoomEntity> chatRoomEntityList = new ArrayList<>();
        chatRoomEntityList.add(chatRoomEntity);
        when(chatRoomRepository.existsByRoomIdAndUserId(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(true);
        when(chatRoomRepository.findAllByOrderByCreatedAtAsc()).thenReturn(chatRoomEntityList);
        assertEquals(1, chatRoomService.getAvailableChatRooms(1L).size());
        verify(chatRoomRepository).existsByRoomIdAndUserId(Mockito.<Long>any(), Mockito.<Long>any());
        verify(chatRoomRepository).findAllByOrderByCreatedAtAsc();
        verify(chatRoomEntity, atLeast(1)).getOwner();
        verify(chatRoomEntity, atLeast(1)).getRoomId();
        verify(chatRoomEntity).getDescription();
        verify(chatRoomEntity).getRoomName();
        verify(chatRoomEntity).setOwner(Mockito.<UserEntity>any());
    }

    /**
     * Testing method:{@link ChatRoomService#createChatRoom(Long, ChatRoom)}
     */
    @Test
    void testCreateChatRoom() {
        when(chatRoomRepository.existsByRoomName(Mockito.<String>any())).thenReturn(true);

        ChatRoom roomRequest = new ChatRoom();
        roomRequest.setChatRoomDescription("Chat Room Description");
        roomRequest.setChatRoomName("Chat Room Name");
        assertThrows(ChatRoomException.class, () -> chatRoomService.createChatRoom(1L, roomRequest));
        verify(chatRoomRepository).existsByRoomName(Mockito.<String>any());
    }

    /**
     * Testing method:{@link ChatRoomService#createChatRoom(Long, ChatRoom)}
     */
    @Test
    void testCreateChatRoom2() {
        when(chatRoomRepository.save(Mockito.<ChatRoomEntity>any())).thenReturn(new ChatRoomEntity());
        when(chatRoomRepository.existsByRoomName(Mockito.<String>any())).thenReturn(false);
        UserEntity userEntity = new UserEntity();
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(userEntity));

        ChatRoom roomRequest = new ChatRoom();
        roomRequest.setChatRoomDescription("Chat Room Description");
        roomRequest.setChatRoomName("Chat Room Name");
        ChatRoomEntity actualCreateChatRoomResult = chatRoomService.createChatRoom(1L, roomRequest);
        assertNull(actualCreateChatRoomResult.getChatRoomMessages());
        assertFalse(actualCreateChatRoomResult.isDeleted());
        assertEquals("Chat Room Name", actualCreateChatRoomResult.getRoomName());
        assertNull(actualCreateChatRoomResult.getRoomId());
        assertSame(userEntity, actualCreateChatRoomResult.getOwner());
        assertEquals(1, actualCreateChatRoomResult.getMembers().size());
        assertEquals("Chat Room Description", actualCreateChatRoomResult.getDescription());
        verify(chatRoomRepository).existsByRoomName(Mockito.<String>any());
        verify(chatRoomRepository).save(Mockito.<ChatRoomEntity>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#createChatRoom(Long, ChatRoom)}
     */
    @Test
    void testCreateChatRoom3() {
        when(chatRoomRepository.save(Mockito.<ChatRoomEntity>any()))
                .thenThrow(new ChatRoomException("An error occurred"));
        when(chatRoomRepository.existsByRoomName(Mockito.<String>any())).thenReturn(false);
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));

        ChatRoom roomRequest = new ChatRoom();
        roomRequest.setChatRoomDescription("Chat Room Description");
        roomRequest.setChatRoomName("Chat Room Name");
        assertThrows(ChatRoomException.class, () -> chatRoomService.createChatRoom(1L, roomRequest));
        verify(chatRoomRepository).existsByRoomName(Mockito.<String>any());
        verify(chatRoomRepository).save(Mockito.<ChatRoomEntity>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#createChatRoom(Long, ChatRoom)}
     */
    @Test
    void testCreateChatRoom4() {

        when(chatRoomRepository.save(Mockito.<ChatRoomEntity>any())).thenReturn(new ChatRoomEntity());
        when(chatRoomRepository.existsByRoomName(Mockito.<String>any())).thenReturn(false);
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));

        ChatRoom roomRequest = new ChatRoom();
        roomRequest.setChatRoomDescription("Chat Room Description");
        roomRequest.setChatRoomName("Chat Room Name");
        ChatRoomEntity chatRoom = chatRoomService.createChatRoom(1L, roomRequest);
        assertEquals(roomRequest.getChatRoomName(), chatRoom.getRoomName());
        assertEquals(roomRequest.getChatRoomDescription(), chatRoom.getDescription());
        verify(chatRoomRepository).save(Mockito.<ChatRoomEntity>any());
        verify(chatRoomRepository).existsByRoomName(Mockito.<String>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());

    }
    /**
     * Testing method:{@link ChatRoomService#joinUserInChatRoom(Long, Long)}
     */
    @Test
    void testJoinUserInChatRoom() {
        when(chatRoomRepository.save(Mockito.<ChatRoomEntity>any())).thenReturn(new ChatRoomEntity());
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new ChatRoomEntity()));
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));
        chatRoomService.joinUserInChatRoom(1L, 1L);
        verify(chatRoomRepository).save(Mockito.<ChatRoomEntity>any());
        verify(chatRoomRepository).findById(Mockito.<Long>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#joinUserInChatRoom(Long, Long)}
     */
    @Test
    void testJoinUserInChatRoom2() {
        when(chatRoomRepository.save(Mockito.<ChatRoomEntity>any()))
                .thenThrow(new ChatRoomException("An error occurred"));
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new ChatRoomEntity()));
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));
        assertThrows(ChatRoomException.class, () -> chatRoomService.joinUserInChatRoom(1L, 1L));
        verify(chatRoomRepository).save(Mockito.<ChatRoomEntity>any());
        verify(chatRoomRepository).findById(Mockito.<Long>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#joinUserInChatRoom(Long, Long)}
     */
    @Test
    void testJoinUserInChatRoom3() {
        when(chatRoomRepository.save(Mockito.<ChatRoomEntity>any())).thenReturn(new ChatRoomEntity());
        UserEntity owner = new UserEntity();
        ArrayList<UserEntity> members = new ArrayList<>();
        when(chatRoomRepository.findById(Mockito.<Long>any()))
                .thenReturn(Optional.of(new ChatRoomEntity(1L, "Room Name",
                        "Room description", owner, members, new ArrayList<>(), true)));
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));
        chatRoomService.joinUserInChatRoom(1L, 1L);
        verify(chatRoomRepository).save(Mockito.<ChatRoomEntity>any());
        verify(chatRoomRepository).findById(Mockito.<Long>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#joinUserInChatRoom(Long, Long)}
     */
    @Test
    void testJoinUserInChatRoom4() {
        ChatRoomEntity chatRoomEntity = mock(ChatRoomEntity.class);
        doNothing().when(chatRoomEntity).addMember(Mockito.<UserEntity>any());
        Optional<ChatRoomEntity> ofResult = Optional.of(chatRoomEntity);
        when(chatRoomRepository.save(Mockito.<ChatRoomEntity>any())).thenReturn(new ChatRoomEntity());
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));
        chatRoomService.joinUserInChatRoom(1L, 1L);
        verify(chatRoomRepository).save(Mockito.<ChatRoomEntity>any());
        verify(chatRoomRepository).findById(Mockito.<Long>any());
        verify(chatRoomEntity).addMember(Mockito.<UserEntity>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#joinUserInChatRoom(Long, Long)}
     */
    @Test
    void testJoinUserInChatRoom5() {
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));
        assertThrows(ChatRoomException.class, () -> chatRoomService.joinUserInChatRoom(1L, 1L));
        verify(chatRoomRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#joinUserInChatRoom(Long, Long)}
     */
    @Test
    void testJoinUserInChatRoom6() {
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(mock(ChatRoomEntity.class)));
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> chatRoomService.joinUserInChatRoom(1L, 1L));
        verify(chatRoomRepository).findById(Mockito.<Long>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#joinUserInChatRoom(Long, Long)}
     */
    @Test
    void testJoinUserInChatRoom7() {
        ChatRoomEntity chatRoomEntity = mock(ChatRoomEntity.class);
        doThrow(new ChatRoomException("An error occurred")).when(chatRoomEntity).addMember(Mockito.<UserEntity>any());
        Optional<ChatRoomEntity> ofResult = Optional.of(chatRoomEntity);
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));
        assertThrows(ChatRoomException.class, () -> chatRoomService.joinUserInChatRoom(1L, 1L));
        verify(chatRoomRepository).findById(Mockito.<Long>any());
        verify(chatRoomEntity).addMember(Mockito.<UserEntity>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }
    
    /**
     * Testing method:{@link ChatRoomService#getChatRoomMessages(Long, Long)}
     */
    @Test
    void testGetChatRoomMessages2() {
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setChatRoomMessages(new ArrayList<>());
        Optional<ChatRoomEntity> ofResult = Optional.of(chatRoomEntity);
        when(chatRoomRepository.findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(chatRoomService.getChatRoomMessages(1L, 1L).isEmpty());
        verify(chatRoomRepository).findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#getChatRoomMessages(Long, Long)}
     */
    @Test
    void testGetChatRoomMessages3() {
        ChatRoomEntity chatRoomEntity = mock(ChatRoomEntity.class);
        when(chatRoomEntity.getChatRoomMessages()).thenReturn(new ArrayList<>());
        Optional<ChatRoomEntity> ofResult = Optional.of(chatRoomEntity);
        when(chatRoomRepository.findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(chatRoomService.getChatRoomMessages(1L, 1L).isEmpty());
        verify(chatRoomRepository).findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any());
        verify(chatRoomEntity).getChatRoomMessages();
    }

    /**
     * Testing method:{@link ChatRoomService#getChatRoomMessages(Long, Long)}
     */
    @Test
    void testGetChatRoomMessages5() {
        when(chatRoomRepository.findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(Optional.empty());
        assertThrows(ChatRoomException.class, () -> chatRoomService.getChatRoomMessages(1L, 1L));
        verify(chatRoomRepository).findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#getChatRoomMessages(Long, Long)}
     */
    @Test
    void testGetChatRoomMessages6() {
        ChatRoomEntity chatRoomEntity = mock(ChatRoomEntity.class);
        when(chatRoomEntity.getChatRoomMessages()).thenThrow(new ChatRoomException("An error occurred"));
        Optional<ChatRoomEntity> ofResult = Optional.of(chatRoomEntity);
        when(chatRoomRepository.findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ChatRoomException.class, () -> chatRoomService.getChatRoomMessages(1L, 1L));
        verify(chatRoomRepository).findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any());
        verify(chatRoomEntity).getChatRoomMessages();
    }


    /**
     * Testing method:{@link ChatRoomService#getChatRoomMessages(Long, Long)}
     */
    @Test
    void testGetChatRoomMessages9() {
        ChatRoomEntity chatRoom = mock(ChatRoomEntity.class);
        when(chatRoom.getRoomId()).thenReturn(1L);
        when(chatRoom.getRoomName()).thenReturn("Room Name");

        ChatRoomMessageEntity chatRoomMessageEntity = new ChatRoomMessageEntity();
        chatRoomMessageEntity.setSender(new UserEntity());
        chatRoomMessageEntity.setChatRoom(chatRoom);

        ArrayList<ChatRoomMessageEntity> chatRoomMessageEntityList = new ArrayList<>();
        chatRoomMessageEntityList.add(chatRoomMessageEntity);
        ChatRoomEntity chatRoomEntity = mock(ChatRoomEntity.class);
        when(chatRoomEntity.getChatRoomMessages()).thenReturn(chatRoomMessageEntityList);
        Optional<ChatRoomEntity> ofResult = Optional.of(chatRoomEntity);
        when(chatRoomRepository.findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, chatRoomService.getChatRoomMessages(1L, 1L).size());
        verify(chatRoomRepository).findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any());
        verify(chatRoomEntity).getChatRoomMessages();
        verify(chatRoom).getRoomId();
        verify(chatRoom).getRoomName();
    }

    /**
     * Testing method:{@link ChatRoomService#getChatRoomMessages(Long, Long)}
     */
    @Test
    void testGetChatRoomMessages10() {
        ChatRoomMessageEntity chatRoomMessageEntity = mock(ChatRoomMessageEntity.class);
        when(chatRoomMessageEntity.getTextMessage()).thenReturn("Text Message");
        when(chatRoomMessageEntity.getSentAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(chatRoomMessageEntity.getSender()).thenReturn(new UserEntity());
        when(chatRoomMessageEntity.getChatRoom()).thenReturn(new ChatRoomEntity());
        doNothing().when(chatRoomMessageEntity).setChatRoom(Mockito.<ChatRoomEntity>any());
        chatRoomMessageEntity.setChatRoom(mock(ChatRoomEntity.class));

        ArrayList<ChatRoomMessageEntity> chatRoomMessageEntityList = new ArrayList<>();
        chatRoomMessageEntityList.add(chatRoomMessageEntity);
        ChatRoomEntity chatRoomEntity = mock(ChatRoomEntity.class);
        when(chatRoomEntity.getChatRoomMessages()).thenReturn(chatRoomMessageEntityList);
        Optional<ChatRoomEntity> ofResult = Optional.of(chatRoomEntity);
        when(chatRoomRepository.findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, chatRoomService.getChatRoomMessages(1L, 1L).size());
        verify(chatRoomRepository).findChatRoomWithUser(Mockito.<Long>any(), Mockito.<Long>any());
        verify(chatRoomEntity).getChatRoomMessages();
        verify(chatRoomMessageEntity, atLeast(1)).getSender();
        verify(chatRoomMessageEntity, atLeast(1)).getChatRoom();
        verify(chatRoomMessageEntity).getTextMessage();
        verify(chatRoomMessageEntity).getSentAt();
        verify(chatRoomMessageEntity).setChatRoom(Mockito.<ChatRoomEntity>any());
    }

    /**
     * Testing method:{@link ChatRoomService#saveChatRoomMessage(ChatRoomMessage)}
     */
    @Test
    void testSaveChatRoomMessage() {
        when(chatRoomMessageRepository.save(Mockito.<ChatRoomMessageEntity>any()))
                .thenReturn(new ChatRoomMessageEntity());
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new ChatRoomEntity()));
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));

        ChatRoomMessage messageRequest = new ChatRoomMessage();
        messageRequest.setChatRoomId(1L);
        messageRequest.setChatRoomName("Chat Room Name");
        messageRequest.setMessageType(MessageType.JOIN);
        messageRequest.setSenderFullName("Mr Narender");
        messageRequest.setSenderId(1L);
        messageRequest.setSentAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        messageRequest.setTextMessage("Text Message");
        ChatRoomMessage actualSaveChatRoomMessageResult = chatRoomService.saveChatRoomMessage(messageRequest);
        assertNull(actualSaveChatRoomMessageResult.getChatRoomId());
        assertEquals("Text Message", actualSaveChatRoomMessageResult.getTextMessage());
        assertNull(actualSaveChatRoomMessageResult.getSenderId());
        assertEquals("00:00", actualSaveChatRoomMessageResult.getSentAt().toLocalTime().toString());
        assertEquals("null null", actualSaveChatRoomMessageResult.getSenderFullName());
        assertEquals(MessageType.MESSAGE, actualSaveChatRoomMessageResult.getMessageType());
        assertNull(actualSaveChatRoomMessageResult.getChatRoomName());
        verify(chatRoomMessageRepository).save(Mockito.<ChatRoomMessageEntity>any());
        verify(chatRoomRepository).findById(Mockito.<Long>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#saveChatRoomMessage(ChatRoomMessage)}
     */
    @Test
    void testSaveChatRoomMessage2() {
        when(chatRoomMessageRepository.save(Mockito.<ChatRoomMessageEntity>any()))
                .thenThrow(new ChatRoomException("An error occurred"));
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new ChatRoomEntity()));
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));

        ChatRoomMessage messageRequest = new ChatRoomMessage();
        messageRequest.setChatRoomId(1L);
        messageRequest.setChatRoomName("Chat Room Name");
        messageRequest.setMessageType(MessageType.JOIN);
        messageRequest.setSenderFullName("Mr Narender");
        messageRequest.setSenderId(1L);
        messageRequest.setSentAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        messageRequest.setTextMessage("Text Message");
        assertThrows(ChatRoomException.class, () -> chatRoomService.saveChatRoomMessage(messageRequest));
        verify(chatRoomMessageRepository).save(Mockito.<ChatRoomMessageEntity>any());
        verify(chatRoomRepository).findById(Mockito.<Long>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#saveChatRoomMessage(ChatRoomMessage)}
     */
    @Test
    void testSaveChatRoomMessage3() {
        when(chatRoomMessageRepository.save(Mockito.<ChatRoomMessageEntity>any()))
                .thenReturn(new ChatRoomMessageEntity());
        ChatRoomEntity chatRoomEntity = mock(ChatRoomEntity.class);
        when(chatRoomEntity.getRoomId()).thenReturn(1L);
        when(chatRoomEntity.getRoomName()).thenReturn("Room Name");
        Optional<ChatRoomEntity> ofResult = Optional.of(chatRoomEntity);
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));

        ChatRoomMessage messageRequest = new ChatRoomMessage();
        messageRequest.setChatRoomId(1L);
        messageRequest.setChatRoomName("Chat Room Name");
        messageRequest.setMessageType(MessageType.JOIN);
        messageRequest.setSenderFullName("Mr Narender");
        messageRequest.setSenderId(1L);
        messageRequest.setSentAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        messageRequest.setTextMessage("Text Message");
        ChatRoomMessage actualSaveChatRoomMessageResult = chatRoomService.saveChatRoomMessage(messageRequest);
        assertEquals(1L, actualSaveChatRoomMessageResult.getChatRoomId().longValue());
        assertEquals("Text Message", actualSaveChatRoomMessageResult.getTextMessage());
        assertNull(actualSaveChatRoomMessageResult.getSenderId());
        assertEquals("00:00", actualSaveChatRoomMessageResult.getSentAt().toLocalTime().toString());
        assertEquals("null null", actualSaveChatRoomMessageResult.getSenderFullName());
        assertEquals(MessageType.MESSAGE, actualSaveChatRoomMessageResult.getMessageType());
        assertEquals("Room Name", actualSaveChatRoomMessageResult.getChatRoomName());
        verify(chatRoomMessageRepository).save(Mockito.<ChatRoomMessageEntity>any());
        verify(chatRoomRepository).findById(Mockito.<Long>any());
        verify(chatRoomEntity).getRoomId();
        verify(chatRoomEntity).getRoomName();
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#saveChatRoomMessage(ChatRoomMessage)}
     */
    @Test
    void testSaveChatRoomMessage5() {
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new UserEntity()));

        ChatRoomMessage messageRequest = new ChatRoomMessage();
        messageRequest.setChatRoomId(1L);
        messageRequest.setChatRoomName("Chat Room Name");
        messageRequest.setMessageType(MessageType.JOIN);
        messageRequest.setSenderFullName("Mr Narender");
        messageRequest.setSenderId(1L);
        messageRequest.setSentAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        messageRequest.setTextMessage("Text Message");
        assertThrows(ChatRoomException.class, () -> chatRoomService.saveChatRoomMessage(messageRequest));
        verify(chatRoomRepository).findById(Mockito.<Long>any());
    }

    /**
     * Testing method:{@link ChatRoomService#saveChatRoomMessage(ChatRoomMessage)}
     */
    @Test
    void testSaveChatRoomMessage8() {
        when(chatRoomRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(mock(ChatRoomEntity.class)));
        when(userEntityRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        new UsernameNotFoundException("Msg");
        new UsernameNotFoundException("Msg");

        ChatRoomMessage messageRequest = new ChatRoomMessage();
        messageRequest.setChatRoomId(1L);
        messageRequest.setChatRoomName("Chat Room Name");
        messageRequest.setMessageType(MessageType.JOIN);
        messageRequest.setSenderFullName("Mr Narender");
        messageRequest.setSenderId(1L);
        messageRequest.setSentAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        messageRequest.setTextMessage("Text Message");
        assertThrows(UsernameNotFoundException.class, () -> chatRoomService.saveChatRoomMessage(messageRequest));
        verify(chatRoomRepository).findById(Mockito.<Long>any());
        verify(userEntityRepository).findById(Mockito.<Long>any());
    }
}

