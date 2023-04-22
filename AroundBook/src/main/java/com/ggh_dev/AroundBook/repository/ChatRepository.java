package com.ggh_dev.AroundBook.repository;

import com.ggh_dev.AroundBook.domain.Chat;
import com.ggh_dev.AroundBook.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private final EntityManager em;

    /**
     * 채팅 저장
     */
    public void save(Chat chat) {
        if (chat.getId() == null) {
            em.persist(chat);   //신규 등록 객체
        }else{
            em.merge(chat); //이미 등록된 객체
        }
    }

    /**
     * 채팅방 채팅 불러오기
     */
    public List<Chat> findAllByChatRoom(ChatRoom chatRoom) {
        return em.createQuery("select c from Chat c where c.chatRoom = :chatRoom", Chat.class)
                .setParameter("chatRoom", chatRoom)
                .getResultList();
    }
}
