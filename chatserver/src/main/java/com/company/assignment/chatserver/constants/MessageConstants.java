package com.company.assignment.chatserver.constants;

public interface MessageConstants {
     String TOPIC_ROOMS = "/topic/chatrooms.";

     String USER_CREATED = "User created successfully.";
     String ROOM_CREATED = "Room created successfully.";
     String USER_JOINED_ROOM = "User added in room successfully.";

    String MESSAGE_SENT_TO_ROOM = "Message sent successfully.";

    String CHAT_ROOM_NOT_EXISTS = "Chat room doesn't exists.";
    String SELECT_VALID_CHAT_ROOM = "Room doesn't exists or you are not a member.";
}
