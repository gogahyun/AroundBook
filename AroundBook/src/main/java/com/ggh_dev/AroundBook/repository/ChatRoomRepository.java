package com.ggh_dev.AroundBook.repository;

import com.ggh_dev.AroundBook.domain.ChatRoom;
import com.ggh_dev.AroundBook.web.Chat.ChatRoomForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private Map<String, ChatRoomForm> chatRoomMap = new LinkedHashMap<>();
    private final EntityManager em;

    public ChatRoomForm createChatRoom() {
        ChatRoomForm chatRoom = ChatRoomForm.createChatRoom();
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);


        return chatRoom;
    }

    public List<ChatRoomForm> findAllRooms() {
        List<ChatRoomForm> result = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(result); //채팅방 생성 최근 순으로 변경

        return result;
    }

    public ChatRoomForm findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    /**
     * 채팅방 등록
     */
    public void save(ChatRoom chatRoom) {
        if (chatRoom.getId() == null) {
            em.persist(chatRoom);
        }else{
            em.merge(chatRoom);
        }
    }

    /**
     * 채팅방 전체 목록 조회
     */
    public List<ChatRoom> findChatRooms() {
        List<ChatRoom> result = em.createQuery("select c from ChatRoom c",ChatRoom.class)
                        .getResultList();
        Collections.reverse(result); //채팅방 생성 최근 순으로 변경

        return result;
    }

    public ChatRoom findChatRoomById(Long id) {
        return em.find(ChatRoom.class, id);
    }

}
