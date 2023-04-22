package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.ChatRoom;
import com.ggh_dev.AroundBook.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방 등록
     */
    @Transactional
    public Long saveChatRoom(Long member1Id, Long member2Id) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.createChatRoom(member1Id, member2Id);
        chatRoomRepository.save(chatRoom);
        return chatRoom.getId();
    }

    /**
     * 회원별 채팅방 목록 조회
     */
    public List<ChatRoom> findChatRooms(Long memberId){
        return chatRoomRepository.findChatRoomsByMemberId(memberId);
    }

    /**
     * 채팅방 조회
     */
    public ChatRoom findChatRoom(Long id) {
        return chatRoomRepository.findChatRoomById(id);
    }

}
