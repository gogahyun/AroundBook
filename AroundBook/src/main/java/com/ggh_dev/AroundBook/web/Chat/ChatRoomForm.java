package com.ggh_dev.AroundBook.web.Chat;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ChatRoomForm {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomForm createChatRoom() {
        ChatRoomForm room = new ChatRoomForm();

        room.roomId= UUID.randomUUID().toString();
        return room;
    }

}
