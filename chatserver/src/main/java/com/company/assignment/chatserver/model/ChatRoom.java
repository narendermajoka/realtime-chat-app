package com.company.assignment.chatserver.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChatRoom {
    @NotBlank
    @Size(min=3, max = 20)
    private String chatRoomName;

    @NotBlank
    @Size(max = 255)
    private String chatRoomDescription;
}
