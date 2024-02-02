package com.company.assignment.chatserver.entity;

import com.company.assignment.chatserver.auth.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chat-room")
@SQLDelete(sql = "UPDATE `chat-room` SET deleted = true WHERE room_id=?")
@Where(clause = "deleted=false")
public class ChatRoomEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;
    @Column(name = "room_name", unique = true, nullable = false, length = 20)
    private String roomName;
    @Column(name = "room_description", length = 255)
    private String description;
    @ManyToOne
    @JoinColumn(
            name = "owner_user_id",
            referencedColumnName = "user_id"
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
    private Set<UserEntity> members;

    public void addMember(UserEntity user){
        if(members==null) {
            members = new HashSet<>();
        }
        members.add(user);
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom", cascade = CascadeType.ALL)
    @OrderBy("createdAt ASC")
    private List<ChatRoomMessageEntity> chatRoomMessages;

    public void addChatRoomMessage(ChatRoomMessageEntity messageEntity){
        if(this.chatRoomMessages==null){
            this.chatRoomMessages = new ArrayList<>();
        }
        this.chatRoomMessages.add(messageEntity);
    }

    @Column(name = "deleted")
    private boolean deleted;

}
