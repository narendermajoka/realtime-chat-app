package com.company.assignment.chatserver.repository;

import com.company.assignment.chatserver.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    boolean existsByRoomName(String roomName);
//    List<ChatRoomEntity> findAllOrderByCreatedAtDesc();
}
