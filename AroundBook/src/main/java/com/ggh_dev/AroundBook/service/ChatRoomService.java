package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.ChatRoom;
import com.ggh_dev.AroundBook.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방 등록
     */
    public void saveChatRoom() {
        ChatRoom chatRoom = new ChatRoom();
        chatRoomRepository.save(chatRoom);
    }

    /**
     * 채팅방 목록 조회
     */
    public List<ChatRoom> findChatRooms(){
        return chatRoomRepository.findChatRooms();
    }

    /**
     * 채팅방 조회
     */
    public ChatRoom findChatRoom(Long id) {
        return chatRoomRepository.findChatRoomById(id);
    }

}
