package com.company.assignment.chatserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity( name = "chat-room-message")
public class ChatRoomMessageEntity  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;
    @ManyToOne
    @JoinColumn(
            name = "sender_user_id",
            referencedColumnName = "user_id",
            nullable = false
    )
    private UserEntity sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "chat_room_id",
            referencedColumnName = "room_id",
            nullable = false
    )
    private ChatRoomEntity chatRoom;

    @Column(name = "text_message")
    private String textMessage;
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
}
