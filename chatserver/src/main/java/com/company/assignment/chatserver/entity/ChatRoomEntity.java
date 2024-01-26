package com.company.assignment.chatserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chat-room")
public class ChatRoomEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;
    @Column(name = "room_name", unique = true, nullable = false)
    private String roomName;
    @ManyToOne
    @JoinColumn(
            name = "owner_user_id",
            referencedColumnName = "user_id",
            nullable = false
    )
    private UserEntity owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "room-user-mapping",
            joinColumns = @JoinColumn(
                    name = "room_id",
                    referencedColumnName = "room_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name="user_id",
                    referencedColumnName = "user_id"
            )
    )
    private List<UserEntity> members;

    public void addMember(UserEntity user){
        if(members==null) {
            members = new ArrayList<>();
        }
        members.add(user);
    }
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "chat_room_id",
            referencedColumnName = "room_id"
    )
    private List<ChatRoomMessageEntity> chatRoomMessages;

}