package com.company.assignment.chatserver.repository;

import com.company.assignment.chatserver.entity.ChatRoomMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomMessageRepository extends JpaRepository<ChatRoomMessageEntity, Long> {

}
