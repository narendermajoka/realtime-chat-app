package com.company.assignment.chatserver.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMessageResponse extends ChatRoomMessage{
    private String senderFullName;
    private String chatRoomName;
}
