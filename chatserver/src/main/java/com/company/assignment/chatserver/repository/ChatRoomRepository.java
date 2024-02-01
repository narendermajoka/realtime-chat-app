package com.company.assignment.chatserver.repository;

import com.company.assignment.chatserver.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    List<ChatRoomEntity> findAllByOrderByCreatedAtAsc();
    boolean existsByRoomId(Long roomId);

    boolean existsByRoomName(String roomName);
    @Query("select r from `chat-room` r inner join r.members m where r.roomId = :roomId and m.userId = :userId")
    Optional<ChatRoomEntity> findChatRoomWithUser(@Param("roomId") Long roomId, @Param("userId") Long userId);
    @Query("select true from `chat-room` r inner join r.members m where r.roomId = :roomId and m.userId = :userId")
    Boolean existsByRoomIdAndUserId(@Param("roomId") Long roomId, @Param("userId") Long userId);
}
