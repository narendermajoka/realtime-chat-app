package com.company.assignment.chatserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponse extends ChatRoom{
    private Long chatRoomId;
    private Long ownerId;
    private String ownerName;
    private boolean isLoggedInUserMember;
}
